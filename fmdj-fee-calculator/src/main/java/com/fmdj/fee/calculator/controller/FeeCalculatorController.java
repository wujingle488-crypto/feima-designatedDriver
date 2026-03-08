package com.fmdj.fee.calculator.controller;
import com.fmdj.common.util.CommonResult;
import com.fmdj.fee.calculator.controller.form.RideChargeCalculatorForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fee")
@Tag(name = "FeeCalculatorController", description = "代驾费计算Web接口")
public class FeeCalculatorController {

    /**
     * 基于代驾开始时间 + 代驾公里数计算费用
     * */
    @PostMapping("/calculator/rideChargeCalculator")
    public CommonResult rideChargeCalculator(@RequestBody @Valid RideChargeCalculatorForm form) {
        //构建返回的 JSON 对象
        Map<String, Object> result = new HashMap<>();
        result.put("amount", "95.00");//总金额
        result.put("chargeRuleId", "1112223334445125");//使用的规则ID
        result.put("baseMileage", "28");//代驾基础里程
        result.put("baseMileagePrice", "65"); //基础里程费
        result.put("exceedMileagePrice", "2.5");//超出规定里程后每公里2.5元
        result.put("mileageFee", "112.50");//本订单里程费
        result.put("baseMinute", "8"); //免费等时8分钟
        result.put("exceedMinutePrice", "2.0");//超出10分钟后，每分钟2元
        result.put("waitingFee", "0.00");//本订单等时费
        result.put("baseReturnMileage", "9");//总里程超过9公里后，要加收返程费
        result.put("exceedReturnPrice", "2.0");//返程里程是每公里2元
        result.put("returnMileage", "12.5");//本订单的返程里程
        result.put("returnFree", "12.5");
        // 返回结果
        return CommonResult.ok().put(CommonResult.RETURN_RESULT, result);
    }
}
