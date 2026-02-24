package com.fmdj.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.fmdj.bff.driver.controller.form.*;
import com.fmdj.bff.driver.service.DriverService;
import com.fmdj.common.util.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController",description = "司机端模块接口")
public class DriverController {
    @Resource
    private DriverService driverService;

    @PostMapping("/registerNewDriver")
    @Operation(summary = "司机注册接口")
    public CommonResult registerDriver(@RequestBody @Valid RegisterDriverForm form) {
        long driverId = driverService.registerDriver(form);
        //因为权限认证是基于SaToken生成的
        StpUtil.login(driverId);
        //返回token信息给客户端
        String tokenValue = StpUtil.getTokenInfo().getTokenValue();
        return CommonResult.ok().put(CommonResult.RETURN_TOKEN, tokenValue);
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "司机实名认证接口")
    @SaCheckLogin
    public CommonResult updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form) {
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        int rows = driverService.updateDriverAuth(form);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }

    @PostMapping("/login")
    @Operation(summary = "司机登录接口")
    public CommonResult login(@RequestBody @Valid LoginForm form) {
        HashMap<String, Object> loginResult = driverService.login(form);
        if (loginResult != null) {
            Long driverId = MapUtil.getLong(loginResult, "id");
            byte realAuth = Byte.parseByte(MapUtil.getStr(loginResult, "realAuth"));
            Boolean archive = MapUtil.getBool(loginResult, "archive");
            StpUtil.login(driverId);
            String token = StpUtil.getTokenInfo().getTokenValue();
            return CommonResult.ok().put(CommonResult.RETURN_TOKEN, token).put("realAuth", realAuth).put("archive", archive);
        }
        return CommonResult.ok();
    }

    @GetMapping("/logout")
    @Operation(summary = "司机退出登录接口")
    public CommonResult logout(){
        StpUtil.logout();
        return CommonResult.ok();
    }

    @PostMapping("/selectDriverInfo")
    @Operation(summary = "查询司机个人信息接口")
    public CommonResult selectDriverInfo() {
        long driverId = StpUtil.getLoginIdAsLong();
        SelectDriverInfoForm selectDriverInfoForm = new SelectDriverInfoForm();
        selectDriverInfoForm.setDriverId(driverId);
        HashMap<String, Object> map = driverService.selectDriverInfo(selectDriverInfoForm);
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, map);
    }

    @PostMapping("/selectWorkbenchData")
    @Operation(summary = "查询司机工作台数据接口")
    @SaCheckLogin
    public CommonResult selectWorkbenchData(){
        long driverId = StpUtil.getLoginIdAsLong();
        HashMap result = driverService.selectWorkbenchData(driverId);
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, result);
    }
}
