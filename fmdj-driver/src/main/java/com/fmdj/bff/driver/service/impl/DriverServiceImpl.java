package com.fmdj.bff.driver.service.impl;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fmdj.common.util.CosUtil;
import com.fmdj.bff.driver.controller.form.*;
import com.fmdj.bff.driver.feign.DrServiceApi;
import com.fmdj.bff.driver.feign.OdrServiceApi;
import com.fmdj.bff.driver.service.DriverService;
import com.fmdj.common.util.CommonResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DriverServiceImpl implements DriverService {
    @Resource
    private DrServiceApi drServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public long registerDriver(RegisterDriverForm form) {
        CommonResult commonResult = drServiceApi.registerDriver(form);
        Object userId = commonResult.get("userId");
        return Long.parseLong(userId.toString());
    }

}
