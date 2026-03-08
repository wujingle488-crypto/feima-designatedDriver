package com.fmdj.bff.driver.service;

import com.fmdj.bff.driver.controller.form.RemoveLocationCacheForm;
import com.fmdj.bff.driver.controller.form.UpdateLocationCacheForm;

public interface DriverPositionService {
    void updatePositionCache(UpdateLocationCacheForm form);

    void removePositionCache(RemoveLocationCacheForm form);
}
