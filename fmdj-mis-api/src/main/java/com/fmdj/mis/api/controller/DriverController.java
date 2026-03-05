package com.fmdj.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.fmdj.common.util.CommonResult;
import com.fmdj.common.util.PageUtils;
import com.fmdj.mis.api.controller.form.SelectDriverByPageForm;
import com.fmdj.mis.api.controller.form.SelectDriverRealAuthInfoForm;
import com.fmdj.mis.api.controller.form.UpdateDriverRealAuthForm;
import com.fmdj.mis.api.service.DriverService;
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
@RequestMapping("/driver")
@Tag(name = "DriverController", description = "司机管理Web接口")
public class DriverController {
    @Resource
    private DriverService driverService;

    @PostMapping("/selectDriverByPage")
    @Operation(summary = "分页查询司机个人信息接口")
    @SaCheckPermission(value = {"ROOT", "DRIVER:SELECT"}, mode = SaMode.OR)
    public CommonResult selectDriverByPage(@RequestBody @Valid SelectDriverByPageForm form) {
        PageUtils pageUtils = driverService.selectDriverByPage(form);
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, pageUtils);
    }

    @PostMapping("/selectDriverAuthInfo")
    @Operation(summary = "司机实名认证信息查询")
    @SaCheckPermission(value = {"ROOT", "DRIVER:SELECT"}, mode = SaMode.OR)
    public CommonResult selectDriverAuthInfo(@RequestBody @Valid SelectDriverRealAuthInfoForm form) {
        HashMap<String, Object> map = driverService.selectDriverAuthInfo(form);
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, map);
    }

    @PostMapping("/updateDriverRealAuth")
    @SaCheckPermission(value = {"ROOT", "DRIVER:UPDATE"}, mode = SaMode.OR)
    @Operation(summary = "更新司机实名认证状态")
    public CommonResult updateDriverRealAuth(@RequestBody @Valid UpdateDriverRealAuthForm form) {
        int rows = driverService.updateDriverRealAuth(form);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }
}
