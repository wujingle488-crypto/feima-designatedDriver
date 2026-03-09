package com.fmdj.mps.service;

import java.util.ArrayList;
import java.util.Map;

public interface DriverPositionService {
    void updatePositionCache(Map param);

    void removePositionCache(long driverId);

    ArrayList selectBefittingDriverAboutOrder(double startPlaceLatitude,
                                              double startPlaceLongitude,
                                              double endPlaceLatitude,
                                              double endPlaceLongitude,
                                              double mileage);
}
