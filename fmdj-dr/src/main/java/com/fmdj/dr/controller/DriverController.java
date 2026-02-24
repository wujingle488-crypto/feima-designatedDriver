package com.fmdj.dr.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.dr.controller.form.LoginForm;
import com.fmdj.dr.controller.form.RegisterDriverForm;
import com.fmdj.dr.controller.form.SelectDriverInfoForm;
import com.fmdj.dr.controller.form.UpdateDriverAuthForm;
import com.fmdj.dr.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController",description = "司机端模块接口")
public class DriverController {
    @Resource
    private DriverService driverService;

    @PostMapping("/registerDriver")
    @Operation(summary = "司机注册")
    public CommonResult registerDriver(@RequestBody @Valid RegisterDriverForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        String driverId = driverService.registerDriver(param);
        //返回给前端注册成功的ID
        return CommonResult.ok().put(CommonResult.RETURN_USER_ID, driverId);
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新司机实名认证信息")
    public CommonResult updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        int rows = driverService.updateDriverAuth(param);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/login")
    @Operation(summary = "司机登录")
    public CommonResult login(@RequestBody @Valid LoginForm form) {
        HashMap<String, Object> loginResult = driverService.login(form.getCode());
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, loginResult);
    }

    @PostMapping("/selectDriverInfo")
    @Operation(summary = "查询司机个人信息")
    public CommonResult selectDriverInfo(@RequestBody @Valid SelectDriverInfoForm form) {
        HashMap<String, Object> map = driverService.selectDriverInfo(form.getDriverId());
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, map);
    }

}
