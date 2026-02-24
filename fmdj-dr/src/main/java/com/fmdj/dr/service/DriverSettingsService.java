package com.fmdj.dr.service;

import java.util.HashMap;

public interface DriverSettingsService {
    HashMap<String, Object> selectDriverSettings(Long driverId);
}
