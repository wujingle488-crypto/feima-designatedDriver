package com.fmdj.odr.service;

import com.fmdj.odr.db.pojo.OrderBillEntity;
import com.fmdj.odr.db.pojo.OrderEntity;

import java.util.HashMap;

public interface OrderService {
    HashMap<String, Object> selectDriverTodayBusinessData(long driverId);

    String insert(OrderEntity orderEntity, OrderBillEntity orderBillEntity);
}
