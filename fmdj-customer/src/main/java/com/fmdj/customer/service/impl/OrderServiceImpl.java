package com.fmdj.customer.service.impl;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.*;
import com.fmdj.customer.feign.CstServiceApi;
import com.fmdj.customer.feign.MapServiceApi;
import com.fmdj.customer.feign.RuleServiceApi;
import com.fmdj.customer.service.CustomerService;
import com.fmdj.customer.service.OrderService;
import com.fmdj.customer.utils.ReturnMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class OrderServiceImpl implements OrderService {
}
