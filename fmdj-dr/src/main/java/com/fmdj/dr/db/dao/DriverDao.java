package com.fmdj.dr.db.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DriverDao {

    long existsDriver(Map map);

    int registerDriver(Map map);

    String getDriverId(String openId);

    int updateDriverAuth(Map params);

    HashMap login(String openId);

    HashMap selectDriverInfo(long driverId);

    ArrayList<HashMap> selectDriverByPage(Map map);
    long selectDriverByCount(Map map);

    HashMap selectDriverAuth(long driverId);

    int updateDriverRealAuth(Map param);

}




