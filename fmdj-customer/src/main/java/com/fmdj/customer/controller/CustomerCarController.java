package com.fmdj.customer.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.*;
import com.fmdj.customer.service.CustomerCarService;
import com.fmdj.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/customer/car")
@Tag(name = "CustomerCarController", description = "客户车辆Web接口")
public class CustomerCarController {
    @Resource
    private CustomerCarService customerCarService;

    @PostMapping("/insertCustomerCar")
    @Operation(summary = "添加客户车辆接口")
    @SaCheckLogin
    public CommonResult insertCustomerCar(@RequestBody @Valid InsertCustomerCarForm form) {
        long customerId = StpUtil.getLoginIdAsLong();
        form.setCustomerId(customerId);
        customerCarService.insertCustomerCar(form);
        return CommonResult.ok();
    }

    @PostMapping("/selectCustomerCarList")
    @Operation(summary = "查询客户车辆信息接口")
    @SaCheckLogin
    public CommonResult selectCustomerCarList(@RequestBody @Valid SelectCustomerCarListForm form){
        long customerId = StpUtil.getLoginIdAsLong();
        form.setCustomerId(customerId);
        ArrayList<HashMap> list = customerCarService.selectCustomerCarList(form);
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, list);
    }

    @PostMapping("/deleteCustomerCarById")
    @Operation(summary = "删除客户车辆接口")
    @SaCheckLogin
    public CommonResult deleteCustomerCarById(@RequestBody @Valid DeleteCustomerCarByIdForm form) {
        int rows = customerCarService.deleteCustomerCarById(form);
        return CommonResult.ok().put(CommonResult.RETURN_ROW, rows);
    }
}
