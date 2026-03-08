package com.fmdj.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.fmdj.bff.driver.controller.form.RemoveLocationCacheForm;
import com.fmdj.bff.driver.controller.form.UpdateLocationCacheForm;
import com.fmdj.bff.driver.service.DriverPositionService;
import com.fmdj.common.util.CommonResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/driver/position")
@Tag(name = "DriverPositionController", description = "司机定位服务Web接口")
public class DriverPositionController {
    @Resource
    private DriverPositionService driverPositionService;

    @PostMapping("/updatePositionCache")
    @Operation(summary = "更新司机GPS定位缓存")
    //@SaCheckLogin
    public CommonResult updatePositionCache(@RequestBody @Valid UpdateLocationCacheForm form) {
        //long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(form.getDriverId());
        driverPositionService.updatePositionCache(form);
        return CommonResult.ok();
    }

}
