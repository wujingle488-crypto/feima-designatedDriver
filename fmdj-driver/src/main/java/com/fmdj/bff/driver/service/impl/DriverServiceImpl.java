package com.fmdj.bff.driver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
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

    @Resource
    private OdrServiceApi odrServiceApi;

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

    @Override
    public HashMap selectWorkbenchData(long driverId) {
        //查询司机当天的业务数据
        SelectDriverTodayBusinessDataForm form1 = new SelectDriverTodayBusinessDataForm();
        form1.setDriverId(driverId);
        CommonResult commonResult1 = odrServiceApi.selectDriverTodayBusinessData(form1);
        HashMap result1 =  (HashMap)commonResult1.get(CommonResult.RETURN_RESULT);

        //查询司机的设置信息
        SelectDriverSettingsForm form2 = new SelectDriverSettingsForm();
        form2.setDriverId(driverId);
        CommonResult commonResult2 = drServiceApi.selectDriverSettings(form2);
        HashMap result2 =  (HashMap)commonResult2.get(CommonResult.RETURN_RESULT);

        HashMap result = new HashMap<>();
        result.put("business", result1);
        result.put("settings", result2);

        return result;
    }

}
