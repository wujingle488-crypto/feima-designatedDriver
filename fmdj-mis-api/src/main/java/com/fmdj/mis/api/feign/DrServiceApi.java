package com.fmdj.mis.api.feign;

import com.fmdj.common.util.CommonResult;
import com.fmdj.mis.api.controller.form.SelectDriverByPageForm;
import com.fmdj.mis.api.controller.form.SelectDriverRealAuthInfoForm;
import com.fmdj.mis.api.controller.form.UpdateDriverRealAuthForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient("fmdj-dr")
public interface DrServiceApi {
    @PostMapping("/driver/selectDriverByPage")
    CommonResult selectDriverByPage(SelectDriverByPageForm form);

    @PostMapping("/driver/selectDriverAuthInfo")
    CommonResult selectDriverAuthInfo(SelectDriverRealAuthInfoForm form);

    @PostMapping("/driver/updateDriverRealAuth")
    CommonResult updateDriverRealAuth(UpdateDriverRealAuthForm form);
}
