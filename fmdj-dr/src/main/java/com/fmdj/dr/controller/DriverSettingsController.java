package com.fmdj.dr.controller;

import com.fmdj.common.util.CommonResult;
import com.fmdj.dr.service.DriverSettingsService;
import com.fmdj.dr.controller.form.SelectDriverSettingsForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/settings")
@Tag(name = "DriverSettingsController",description = "司机设置模块接口")
public class DriverSettingsController {
    @Resource
    private DriverSettingsService driverSettingsService;

    @PostMapping("/selectDriverSettings")
    @Operation(summary = "查询司机设置")
    public CommonResult selectDriverSettings(@RequestBody @Valid SelectDriverSettingsForm form) {
        HashMap<String, Object> result = driverSettingsService.selectDriverSettings(form.getDriverId());
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, result);
    }
}
