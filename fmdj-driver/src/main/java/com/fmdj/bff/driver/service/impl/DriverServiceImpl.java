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

    @Override
    @Transactional
    @LcnTransaction
    public int updateDriverAuth(UpdateDriverAuthForm form) {
        CommonResult commonResult = drServiceApi.updateDriverAuth(form);
        Object rowsObj = commonResult.get(CommonResult.RETURN_ROW);
        return Integer.parseInt(rowsObj.toString());
    }

    @Override
    public HashMap<String, Object> login(LoginForm form) {
        CommonResult loginResult = drServiceApi.login(form);
        HashMap<String, Object> map = (HashMap<String, Object>) loginResult.get(CommonResult.RETURN_RESULT);
        return map;
    }

    @Override
    public HashMap<String, Object> selectDriverInfo(SelectDriverInfoForm form) {
        CommonResult commonResult = drServiceApi.selectDriverInfo(form);
        return (HashMap<String, Object>) commonResult.get(CommonResult.RETURN_RESULT);
    }
}
