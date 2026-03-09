package com.fmdj.mps.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.fmdj.mps.service.DriverPositionService;
import com.fmdj.mps.util.CoordinateTransform;
import lombok.extern.slf4j.Slf4j;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DriverPositionServiceImpl implements DriverPositionService {
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void updatePositionCache(Map param) {
        Integer driverId = MapUtil.getInt(param, "driverId");
        String latitude = MapUtil.getStr(param, "latitude");
        String longitude = MapUtil.getStr(param, "longitude");
        /**
         * 定向接单相关内容
         */
        //接收几公里内的订单
        int rangeDistance = MapUtil.getInt(param, "rangeDistance");
        //接收代驾里程几公里以上的订单
        int orderDistance = MapUtil.getInt(param, "orderDistance");

        Point point = new Point(Convert.toDouble(longitude), Convert.toDouble(latitude));
        //将司机位置信息存储到redis中
        redisTemplate.opsForGeo().add("driver_location", point, String.valueOf(driverId));

        // 获取定向接单经纬度，如果没有则为 null
        String orientateLongitude = MapUtil.getStr(param, "orientateLongitude", null);
        String orientateLatitude = MapUtil.getStr(param, "orientateLatitude", null);

        String orientation = "none";
        if (orientateLongitude != null && orientateLatitude != null) {
            orientation = orientateLatitude + "," + orientateLongitude;
        }

        //将司机状态信息保存到 Redis，存储时长为 60 秒
        /**
         * 这条信息主要作用是用于判断当前司机是否还在线，只有当司机下线后该缓存才会过期，当用户查询附近的司机时
         * 会先去判断该司机是否已经下线
         * 如果没有下线，在获取到司机的位置信息
         */
        String driverStatus = String.format("%d#%d#%s", rangeDistance, orderDistance, orientation);
        redisTemplate.opsForValue().set("driver_online#" + driverId, driverStatus, 60, TimeUnit.SECONDS);
    }

    @Override
    public void removePositionCache(long driverId) {
        redisTemplate.opsForGeo().remove("driver_location", String.valueOf(driverId));
        redisTemplate.delete("driver_online#" + driverId);
    }

    @Override
    public ArrayList selectBefittingDriverAboutOrder(double startPlaceLatitude, double startPlaceLongitude,
                                                     double endPlaceLatitude, double endPlaceLongitude, double mileage) {

        // 创建一个以订单起始点为中心，半径为5公里的搜索区域
        Point point = new Point(startPlaceLongitude, startPlaceLatitude);//订单起始点
        Metric metric = RedisGeoCommands.DistanceUnit.KILOMETERS;//公里作为单位
        Distance distance = new Distance(5, metric);
        Circle circle = new Circle(point, distance);

        // 创建GEO参数，包含距离、坐标信息，并按升序排列结果
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance() // 包含司机与起点的距离
                .includeCoordinates() // 包含司机的坐标信息
                .sortAscending(); // 按距离升序排列，最近的司机在前

        //查询符合条件的司机
        GeoResults<RedisGeoCommands.GeoLocation<String>> radius = null;
        try {
            radius = redisTemplate.opsForGeo().radius("driver_location", circle, args);//在circle范围内查询args形式driver_location中的参数信息
        } catch (Exception e) {
            log.error("Redis查询失败: {}", e.getMessage());
            // Redis查询失败时返回空列表，避免服务中断
            return new ArrayList<>();
        }

        //创建一个复合条件的司机列表
        List<HashMap<String, Object>> list = new ArrayList<>();

        if (radius != null) {
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : radius) {
                try {
                    RedisGeoCommands.GeoLocation<String> content = result.getContent();//获取司机的位置信息
                    String driverId = content.getName();//获取司机Id
                    double dist = result.getDistance().getValue(); // 获取司机与订单起点的距离

                    //从redis中获取司机的在线状态
                    Object obj = null;
                    try {
                        obj = redisTemplate.opsForValue().get("driver_online#" + driverId);
                    } catch (Exception e) {
                        log.warn("获取司机在线状态失败, driverId: {}, error: {}", driverId, e.getMessage());
                        continue;
                    }

                    if (obj == null) {
                        continue;
                    }

                    String[] temp = obj.toString().split("#");
                    // 验证在线状态数据格式
                    if (temp.length < 3) {
                        log.warn("司机在线状态数据格式错误, driverId: {}, data: {}", driverId, obj);
                        continue;
                    }

                    int rangeDistance = 0;
                    int orderDistance = 0;
                    try {
                        rangeDistance = Integer.parseInt(temp[0]);
                        orderDistance = Integer.parseInt(temp[1]);
                    } catch (NumberFormatException e) {
                        log.warn("司机在线状态数据解析失败, driverId: {}, data: {}, error: {}",
                                driverId, obj, e.getMessage());
                        continue;
                    }
                    String orientation = temp[2];

                    // 判断司机是否在其接单范围内，及订单里程是否符合司机的接单要求
                    if (dist > rangeDistance || !isWithinOrderDistance(orderDistance, mileage)) {
                        continue;
                    }

                    // 判断司机的定向条件是否符合，若定向为"none"，则不限制，否则需要进一步判断
                    boolean bool_3 = false;
                    if (orientation.equals("none")) {
                        bool_3 = true;
                    } else {
                        try {
                            String[] orientationParts = orientation.split(",");
                            if (orientationParts.length >= 2) {
                                double orientationLat = Double.parseDouble(orientationParts[0]);
                                double orientationLon = Double.parseDouble(orientationParts[1]);
                                bool_3 = isWithinOrientation(orientationLat, orientationLon,
                                        endPlaceLatitude, endPlaceLongitude);
                            } else {
                                log.warn("定向位置数据格式错误, driverId: {}, orientation: {}", driverId, orientation);
                            }
                        } catch (NumberFormatException e) {
                            log.warn("定向位置数据解析失败, driverId: {}, orientation: {}, error: {}",
                                    driverId, orientation, e.getMessage());
                        } catch (Exception e) {
                            log.warn("定向位置判断失败, driverId: {}, orientation: {}, error: {}",
                                    driverId, orientation, e.getMessage());
                        }
                    }

                    //如果条件都符合，将司机加入结果集
                    if (bool_3) {
                        HashMap<String, Object> map = new HashMap<>(){{
                            put("driverId", driverId);
                            put("distance", dist);
                        }};
                        list.add(map);
                    }
                } catch (Exception e) {
                    log.warn("处理司机数据时发生异常: {}", e.getMessage());
                    // 继续处理下一个司机
                    continue;
                }
            }
        }
        return new ArrayList<>(list);
    }

    /**
     * 检查订单终点是否在司机的定向位置范围内
     * @param orientationLat 定向位置的纬度
     * @param orientationLon 定向位置的经度
     * @param endLat 订单终点的纬度
     * @param endLon 订单终点的经度
     * @return 如果订单终点在定向位置3000米范围内，则返回true，否则返回false
     */
    private boolean isWithinOrientation(double orientationLat, double orientationLon, double endLat, double endLon) {
        //将定向点的火星坐标转换为GPS坐标
        double[] orientationCoords = CoordinateTransform.
                transformGCJ02ToWGS84(orientationLon, orientationLat);

        //将订单重点火星坐标转换为GPS坐标
        double[] endCoords = CoordinateTransform.
                transformGCJ02ToWGS84(endLon, endLat);

        // 创建GlobalCoordinates对象，用于计算两点之间的距离
        GlobalCoordinates point1 = new GlobalCoordinates(orientationCoords[1], orientationCoords[0]);
        GlobalCoordinates point2 = new GlobalCoordinates(endCoords[1], endCoords[0]);

        // 使用GeodeticCalculator计算两个GPS坐标之间的距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, point1, point2);

        // 判断订单终点是否在定向位置的3000米范围内
        return geoCurve.getEllipsoidalDistance() <= 3000;
    }

    /**
     * 检查订单的里程是否在司机的接单里程范围内
     * @param orderDistance 司机的接单里程范围
     * @param mileage 订单的行驶里程
     * @return 如果里程符合司机的要求，则返回true，否则返回false
     */
    private boolean isWithinOrderDistance(int orderDistance, double mileage) {
        return (orderDistance == 0) ||
                (orderDistance == 5 && mileage > 0 && mileage <= 5) ||
                (orderDistance == 10 && mileage > 5 && mileage <= 10) ||
                (orderDistance == 15 && mileage > 10 && mileage <= 15) ||
                (orderDistance == 30 && mileage > 15 && mileage <= 300);
    }
}
