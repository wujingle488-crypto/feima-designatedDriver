package com.fmdj.dr.controller;

import cn.hutool.core.bean.BeanUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.dr.controller.form.RegisterDriverForm;
import com.fmdj.dr.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController",description = "司机端模块接口")
public class DriverController {
    @Resource
    private DriverService driverService;

    @PostMapping("/registerDriver")
    @Operation(summary = "司机注册接口")
    public CommonResult registerDriver(@RequestBody @Valid RegisterDriverForm form) {
        Map<String, Object> param = BeanUtil.beanToMap(form);
        String driverId = driverService.registerDriver(param);
        //返回给前端注册成功的ID
        return CommonResult.ok().put(CommonResult.RETURN_USER_ID, driverId);
    }

}
