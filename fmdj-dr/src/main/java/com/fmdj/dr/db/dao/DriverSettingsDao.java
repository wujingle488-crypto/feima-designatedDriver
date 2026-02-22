package com.fmdj.dr.db.dao;

import com.fmdj.dr.db.pojo.DriverSettingsEntity;

/**
 * @Entity com.example.hxdsdr.db.pojo.DriverSettingsEntity
 */
public interface DriverSettingsDao {

    public int insertDriversSettings(DriverSettingsEntity entity);

    public String selectDriverSettings(long driverId);


}




