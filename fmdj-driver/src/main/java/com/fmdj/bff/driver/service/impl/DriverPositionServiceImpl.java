package com.fmdj.bff.driver.service.impl;

import com.fmdj.bff.driver.controller.form.RemoveLocationCacheForm;
import com.fmdj.bff.driver.controller.form.UpdateLocationCacheForm;
import com.fmdj.bff.driver.feign.MpsServiceApi;
import com.fmdj.bff.driver.service.DriverPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class DriverPositionServiceImpl implements DriverPositionService {
    @Resource
    private MpsServiceApi mpsServiceApi;

    @Override
    public void updatePositionCache(UpdateLocationCacheForm form) {
        mpsServiceApi.updatePositionCache(form);
    }

    @Override
    public void removePositionCache(RemoveLocationCacheForm form) {
        mpsServiceApi.removePositionCache(form);
    }
}
