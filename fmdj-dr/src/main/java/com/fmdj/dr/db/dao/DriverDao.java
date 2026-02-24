package com.fmdj.dr.db.dao;

import java.util.HashMap;
import java.util.Map;

public interface DriverDao {

    long existsDriver(Map map);

    int registerDriver(Map map);

    String getDriverId(String openId);

    int updateDriverAuth(Map map);

    HashMap<String, Object> login(String openId);

    HashMap<String, Object> selectDriverInfo(long driverId);

    HashMap<String, Object> selectDriverInfoById(long driverId);
}




