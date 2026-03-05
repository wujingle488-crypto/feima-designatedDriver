package com.fmdj.dr.service;

import com.fmdj.common.util.PageUtils;

import java.util.HashMap;
import java.util.Map;

public interface DriverService {
    String registerDriver(Map param);

    int updateDriverAuth(Map map);

    HashMap<String, Object> login(String code);

    HashMap<String, Object> selectDriverInfo(long driverId);

    HashMap<String, Object> selectDriverInfoById(long driverId);

    PageUtils selectDriverByPage(Map param);

    HashMap selectDriverAuthInfo(long driverId);

    int updateDriverRealAuth(Map param);
}
