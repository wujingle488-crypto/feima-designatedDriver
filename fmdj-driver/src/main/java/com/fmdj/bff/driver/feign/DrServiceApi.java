package com.fmdj.bff.driver.feign;
import com.fmdj.common.util.CommonResult;
import com.fmdj.bff.driver.controller.form.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient("fmdj-dr")
public interface DrServiceApi {
    @PostMapping("/driver/registerDriver")
    CommonResult registerDriver(RegisterDriverForm form);

    @PostMapping("/driver/updateDriverAuth")
    CommonResult updateDriverAuth(UpdateDriverAuthForm form);
}
