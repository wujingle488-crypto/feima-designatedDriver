package com.fmdj.bff.driver.feign;

import com.fmdj.bff.driver.controller.form.RemoveLocationCacheForm;
import com.fmdj.bff.driver.controller.form.UpdateLocationCacheForm;
import com.fmdj.common.util.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("fmdj-mps")
public interface MpsServiceApi {
    @PostMapping("/driver/position/updatePositionCache")
    CommonResult updatePositionCache(UpdateLocationCacheForm form);

    @PostMapping("/driver/position/removePositionCache")
    CommonResult removePositionCache(RemoveLocationCacheForm form);
}
