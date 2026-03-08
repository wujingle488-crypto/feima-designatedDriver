package com.fmdj.mps.service;

import java.util.Map;

public interface DriverPositionService {
    void updatePositionCache(Map param);

    void removePositionCache(long driverId);
}
