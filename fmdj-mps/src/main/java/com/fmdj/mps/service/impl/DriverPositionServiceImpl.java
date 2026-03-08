package com.fmdj.mps.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.fmdj.mps.service.DriverPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

        String orientation = (orientateLatitude != null && orientateLongitude != null) ?
                orientateLatitude + "," + orientateLongitude : "null";

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
}
