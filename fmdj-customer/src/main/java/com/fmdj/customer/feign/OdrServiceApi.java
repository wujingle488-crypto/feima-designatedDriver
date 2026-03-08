package com.fmdj.customer.feign;

import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.InsertOrderForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "fmdj-odr")
public interface OdrServiceApi {
    @PostMapping("/order/insertOrder")
    CommonResult insertOrder(InsertOrderForm form);
}
