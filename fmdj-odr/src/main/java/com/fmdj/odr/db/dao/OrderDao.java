package com.fmdj.odr.db.dao;

import com.fmdj.odr.db.pojo.OrderEntity;

import java.util.HashMap;

public interface OrderDao {
    HashMap selectDriverTodayBusinessData(long driverId);

    int insert(OrderEntity entity);

    String selectOrderIdByUUID(String uuid);
}




