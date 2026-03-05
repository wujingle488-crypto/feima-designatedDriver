package com.fmdj.customer.feign;

import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.LoginForm;
import com.fmdj.customer.controller.form.RegisterNewCustomerForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "fmdj-cst")
public interface CstServiceApi {
    @PostMapping("/customer/registerCustomer")
    CommonResult registerNewCustomer(RegisterNewCustomerForm form);
}
