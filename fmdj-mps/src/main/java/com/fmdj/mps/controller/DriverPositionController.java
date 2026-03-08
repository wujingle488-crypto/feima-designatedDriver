package com.fmdj.mps.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.mps.controller.form.RemoveLocationCacheForm;
import com.fmdj.mps.controller.form.UpdateLocationCacheForm;
import com.fmdj.mps.service.DriverPositionService;
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
    public CommonResult updatePositionCache(@RequestBody @Valid UpdateLocationCacheForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        driverPositionService.updatePositionCache(param);
        return CommonResult.ok();
    }

    @PostMapping("/removePositionCache")
    @Operation(summary = "删除司机GPS定位缓存")
    public CommonResult removePositionCache(@RequestBody @Valid RemoveLocationCacheForm form) {
        driverPositionService.removePositionCache(form.getDriverId());
        return CommonResult.ok();
    }
}
