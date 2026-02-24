package com.fmdj.odr.service.impl;

import cn.hutool.core.map.MapUtil;
import com.fmdj.odr.db.dao.OrderDao;
import com.fmdj.odr.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Override
    public HashMap<String, Object> selectDriverTodayBusinessData(long driverId) {
        HashMap result = orderDao.selectDriverTodayBusinessData(driverId);
        String duration = MapUtil.getStr(result, "duration");
        if (duration == null) {
            //duration = "0";
            //测试数据
            duration = "23";
        }
        result.put("duration", duration);

        String income = MapUtil.getStr(result, "income");
        if (income == null) {
            //duration = "0";
            //测试数据
            income = "378.00";
        }
        result.put("income", income);

        return result;
    }
}
