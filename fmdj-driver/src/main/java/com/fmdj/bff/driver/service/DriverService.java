package com.fmdj.bff.driver.service;
import com.fmdj.bff.driver.controller.form.*;
import com.fmdj.bff.driver.feign.DrServiceApi;

import javax.annotation.Resource;
import java.util.HashMap;

public interface DriverService {
    long registerDriver(RegisterDriverForm form);
}
