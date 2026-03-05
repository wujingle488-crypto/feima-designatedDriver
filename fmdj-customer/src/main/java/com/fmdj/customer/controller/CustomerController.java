package com.fmdj.customer.controller;
import cn.dev33.satoken.stp.StpUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.LoginForm;
import com.fmdj.customer.controller.form.RegisterNewCustomerForm;
import com.fmdj.customer.service.CustomerService;
import com.fmdj.customer.utils.ReturnMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@Tag(name = "CustomerController", description = "客户Web接口")
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping("/registerNewCustomer")
    @Operation(summary = "注册新乘客")
    public CommonResult registerNewCustomer(@RequestBody @Valid RegisterNewCustomerForm form) {
        Long customerId = customerService.registerNewCustomer(form);
        StpUtil.login(customerId);
        String token = StpUtil.getTokenInfo().getTokenValue();
        return CommonResult.ok().put(CommonResult.RETURN_TOKEN, token);
    }

}
