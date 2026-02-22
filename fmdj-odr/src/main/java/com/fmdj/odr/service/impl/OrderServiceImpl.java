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
}
