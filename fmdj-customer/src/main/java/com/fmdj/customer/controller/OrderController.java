package com.fmdj.customer.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.CreateNewOrderForm;
import com.fmdj.customer.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/order")
@Tag(name = "OrderController",description = "订单模块Web接口")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/createNewOrder")
    @Operation(summary = "创建新订单")
    //@SaCheckLogin
    public CommonResult createNewOrder(@RequestBody @Valid CreateNewOrderForm form) {
        //long customerId = StpUtil.getLoginIdAsLong();
        form.setCustomerId(1030860760882627552l);
        //form.setCustomerId(form.getCustomerId());
        HashMap result = orderService.createOrder(form);
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, result);
    }
}
