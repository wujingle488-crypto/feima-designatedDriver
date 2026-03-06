package com.fmdj.cst.service.impl;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.exception.GlobalException;
import com.fmdj.common.util.MicroAppUtil;
import com.fmdj.cst.db.dao.CustomerDao;
import com.fmdj.cst.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerDao customerDao;

    @Resource
    private MicroAppUtil microAppUtil;

    @Override
    @Transactional
    @LcnTransaction
    public String registerNewCustomer(Map param) {
        String code = MapUtil.getStr(param, "code");
        String openId = microAppUtil.getOpenId(code);
        HashMap map = new HashMap<>(){{
            put("openId", openId);
        }};
        if (customerDao.hasCustomer(map) != 0) {
            throw new GlobalException("该微信已经注册过");
        }
        param.put("openId", openId);
        customerDao.registerNewCustomer(param);

        return customerDao.selectCustomerIdByOpenId(openId);
    }

    @Override
    public String login(String code) {
        String openId = microAppUtil.getOpenId(code);
        return customerDao.login(openId);
    }
}
