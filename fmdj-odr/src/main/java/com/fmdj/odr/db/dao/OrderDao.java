package com.fmdj.odr.db.dao;
import com.fmdj.odr.db.pojo.OrderEntity;

import java.util.HashMap;

public interface OrderDao {

    public HashMap selectDriverTodayBusinessData(long driverId);

    public int insert(OrderEntity entity);

    public String selectOrderIdByUUID(String uuid);
    
}




