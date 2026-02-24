package com.fmdj.bff.driver.feign;

import com.fmdj.bff.driver.controller.form.*;
import com.fmdj.common.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("fmdj-dr")
public interface DrServiceApi {
    @PostMapping("/driver/registerDriver")
    CommonResult registerDriver(RegisterDriverForm form);

    @PostMapping("/driver/updateDriverAuth")
    CommonResult updateDriverAuth(UpdateDriverAuthForm form);

    @PostMapping("/driver/login")
    CommonResult login(LoginForm form);

    @PostMapping("/driver/selectDriverInfo")
    CommonResult selectDriverInfo(SelectDriverInfoForm form);

    @PostMapping("/settings/selectDriverSettings")
    CommonResult selectDriverSettings(SelectDriverSettingsForm form);

    @PostMapping("/driver/selectDriverAuth")
    CommonResult selectDriverAuth(SelectDriverAuthForm form);
}
