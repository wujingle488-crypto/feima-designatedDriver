package com.fmdj.mis.api.feign;

import com.fmdj.common.util.CommonResult;
import com.fmdj.mis.api.controller.form.SelectDriverByPageForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("fmdj-dr")
public interface DrServiceApi {

}
