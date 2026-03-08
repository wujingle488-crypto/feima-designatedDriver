package com.fmdj.customer.feign;

import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "fmdj-cst")
public interface CstServiceApi {
    @PostMapping("/customer/registerCustomer")
    CommonResult registerNewCustomer(RegisterNewCustomerForm form);

    @PostMapping("/customer/login")
    CommonResult login(LoginForm form);

    @PostMapping("/customer/car/insertCustomerCar")
    CommonResult insertCustomerCar(InsertCustomerCarForm form);

    @PostMapping("/customer/car/selectCustomerCarList")
    CommonResult selectCustomerCarList(SelectCustomerCarListForm form);

    @PostMapping("/customer/car/deleteCustomerCarById")
    CommonResult deleteCustomerCarById(DeleteCustomerCarByIdForm form);
}
