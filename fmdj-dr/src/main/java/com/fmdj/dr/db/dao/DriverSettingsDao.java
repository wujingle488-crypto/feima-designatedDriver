package com.fmdj.dr.db.dao;

import com.fmdj.dr.db.pojo.DriverSettingsEntity;

import java.util.HashMap;

public interface DriverSettingsDao {

    int insertDriversSettings(DriverSettingsEntity entity);

    String selectDriverSettings(Long driverId);
}




