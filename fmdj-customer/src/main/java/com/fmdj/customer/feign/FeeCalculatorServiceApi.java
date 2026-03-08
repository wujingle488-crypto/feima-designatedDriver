package com.fmdj.customer.feign;

import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.RideChargeCalculatorForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 计费系统
 * */
@FeignClient(value = "fmdj-fee-calculator")
public interface FeeCalculatorServiceApi {

    /**
     * 基于代驾开始时间 + 代驾公里数计算费用
     * */
    @PostMapping("/fee/calculator/rideChargeCalculator")
    CommonResult rideChargeCalculator(RideChargeCalculatorForm form);
}