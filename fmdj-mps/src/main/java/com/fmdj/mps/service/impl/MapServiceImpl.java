package com.fmdj.mps.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fmdj.common.exception.GlobalException;
import com.fmdj.mps.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class MapServiceImpl implements MapService {
    //预估里程的API地址
    private String distanceUrl = "https://apis.map.qq.com/ws/distance/v1/matrix/";

    //规划行进路线的API地址
    private String directionUrl = "https://apis.map.qq.com/ws/direction/v1/driving/";

    @Value("${tencent.map.key}")
    private String key;

    @Override
    public HashMap calculateTripDistanceAndDuration(String mode, String startPlaceLatitude, String startPlaceLongitude, String endPlaceLatitude, String endPlaceLongitude) {
        HttpRequest httpRequest = new HttpRequest(distanceUrl);
        httpRequest.form("mode", mode);
        httpRequest.form("from", startPlaceLatitude + "," + startPlaceLongitude);
        httpRequest.form("to", endPlaceLatitude+ "," + endPlaceLongitude);
        httpRequest.form("key", key);

        HttpResponse httpResponse = httpRequest.execute();
        JSONObject jsonObject = JSONUtil.parseObj(httpResponse.body());
        Integer status = jsonObject.getInt("status");
        String message = jsonObject.getStr("message");
        if (status != 0) {
            log.error(message);
            throw new GlobalException("预估里程异常：" + message);
        }
        JSONArray rows = jsonObject.getJSONObject("result").getJSONArray("rows");
        JSONObject element = JSONUtil.parseObj(JSONUtil.parseObj(rows.get(0)).getJSONArray("elements").get(0));
        Integer distance = element.getInt("distance");
        Integer duration = element.getInt("duration");
        String mileage = new BigDecimal(distance).divide(new BigDecimal(1000)).toString();
        int minute = Integer.parseInt(new BigDecimal(duration).divide(new BigDecimal(60), 0, RoundingMode.CEILING).toString());

        HashMap map = new HashMap<>(){{
            put("mileage", mileage);
            put("minute", minute);
        }};
        return map;
    }

    @Override
    public HashMap calculateBestDrivingLine(String startPlaceLatitude, String startPlaceLongitude, String endPlaceLatitude, String endPlaceLongitude) {
        HttpRequest httpRequest = new HttpRequest(directionUrl);
        httpRequest.form("from", startPlaceLatitude + "," + startPlaceLongitude);
        httpRequest.form("to", endPlaceLatitude+ "," + endPlaceLongitude);
        httpRequest.form("key", key);

        HttpResponse httpResponse = httpRequest.execute();
        JSONObject jsonObject = JSONUtil.parseObj(httpResponse.body());
        Integer status = jsonObject.getInt("status");
        String message = jsonObject.getStr("message");
        if (status != 0) {
            log.error(message);
            throw new GlobalException("预估里程异常：" + message);
        }
        JSONObject result = jsonObject.getJSONObject("result");
        HashMap map = result.toBean(HashMap.class);
        return map;
    }
}
