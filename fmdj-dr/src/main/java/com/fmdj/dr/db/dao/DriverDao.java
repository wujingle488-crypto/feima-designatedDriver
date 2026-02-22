package com.fmdj.dr.db.dao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DriverDao {

    long existsDriver(Map map);

    int registerDriver(Map map);

    String getDriverId(String openId);

}




