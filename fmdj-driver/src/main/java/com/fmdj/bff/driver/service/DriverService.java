package com.fmdj.bff.driver.service;
import com.fmdj.bff.driver.controller.form.*;
import com.fmdj.bff.driver.feign.DrServiceApi;

import javax.annotation.Resource;
import java.util.HashMap;

public interface DriverService {
    long registerDriver(RegisterDriverForm form);

    int updateDriverAuth(UpdateDriverAuthForm form);

    HashMap<String, Object> login(LoginForm form);

    HashMap<String, Object> selectDriverInfo(SelectDriverInfoForm form);

    HashMap selectWorkbenchData(long driverId);

    HashMap selectDriverInfoById(SelectDriverAuthForm form);
}
