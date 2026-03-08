package com.fmdj.odr.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.exception.GlobalException;
import com.fmdj.odr.db.dao.OrderBillDao;
import com.fmdj.odr.db.dao.OrderDao;
import com.fmdj.odr.db.pojo.OrderBillEntity;
import com.fmdj.odr.db.pojo.OrderEntity;
import com.fmdj.odr.service.OrderService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private OrderBillDao orderBillDao;

    @Resource
    private RedisTemplate redisTemplate;

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

    @Override
    @Transactional
    @LcnTransaction
    public String insert(OrderEntity orderEntity, OrderBillEntity orderBillEntity) {
        //插入订单记录
        int rows = orderDao.insert(orderEntity);
        if (rows == 1) {
            String orderId = orderDao.selectOrderIdByUUID(orderEntity.getUuid());
            //插入订单费用记录
            orderBillEntity.setOrderId(Long.parseLong(orderId));
            rows = orderBillDao.insert(orderBillEntity);
            if (rows == 1) {
                /*
                 * 将订单信息保存到redis中，后期司机抢单的时候，可以基于redis的事务来避免同时多个司机枪单成功。
                 * key为order#orderId , value为空，expire时间为1分钟。
                 * */
                redisTemplate.opsForValue().set("order#" + orderId, null, 1, TimeUnit.MINUTES);
                return orderId;
            } else {
                throw new GlobalException("保存新订单费用失败");
            }
        } else {
            throw new GlobalException("保存新订单失败");
        }
    }
}
