package com.fmdj.customer.feign;

import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.CalculateTripDistanceAndDurationForm;
import com.fmdj.customer.controller.form.SearchBefittingDriverAboutOrderForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * 规则计费系统
 * */
@FeignClient(value = "fmdj-mps")
public interface MapServiceApi {
    @PostMapping("/map/calculateTripDistanceAndDuration")
    CommonResult calculateTripDistanceAndDuration(CalculateTripDistanceAndDurationForm form);

    @PostMapping("/driver/position/selectBefittingDriverAboutOrder")
    CommonResult selectBefittingDriverAboutOrder(SearchBefittingDriverAboutOrderForm form);
}
