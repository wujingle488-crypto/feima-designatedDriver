package com.fmdj.bff.driver.feign;

import com.fmdj.bff.driver.controller.form.SelectDriverTodayBusinessDataForm;
import com.fmdj.common.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("fmdj-odr")
public interface OdrServiceApi {
    @PostMapping("/order/selectDriverTodayBusinessData")
    CommonResult selectDriverTodayBusinessData(SelectDriverTodayBusinessDataForm form);
}
