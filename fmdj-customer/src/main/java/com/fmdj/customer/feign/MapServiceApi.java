package com.fmdj.customer.feign;
import com.fmdj.common.util.CommonResult;
import com.fmdj.customer.controller.form.EstimateOrderChargeForm;
import com.fmdj.customer.controller.form.EstimateOrderMileageAndMinuteForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 规则计费系统
 * */
@FeignClient(value = "fmdj-rule")
public interface MapServiceApi {

}
