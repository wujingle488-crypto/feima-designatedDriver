package com.fmdj.odr.service;

import java.util.HashMap;

public interface OrderService {
    HashMap<String, Object> selectDriverTodayBusinessData(long driverId);
}
