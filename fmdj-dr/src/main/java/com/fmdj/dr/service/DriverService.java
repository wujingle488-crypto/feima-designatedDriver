package com.fmdj.dr.service;

import java.util.HashMap;
import java.util.Map;

public interface DriverService {
    String registerDriver(Map param);

    int updateDriverAuth(Map map);

    HashMap<String, Object> login(String code);

    HashMap<String, Object> selectDriverInfo(long driverId);
}
