package com.fmdj.mps.service;

import java.util.HashMap;

public interface MapService {
    HashMap calculateTripDistanceAndDuration(String mode,
                                             String startPlaceLatitude,
                                             String startPlaceLongitude,
                                             String endPlaceLatitude,
                                             String endPlaceLongitude);

    HashMap calculateBestDrivingLine(String startPlaceLatitude,
                                     String startPlaceLongitude,
                                     String endPlaceLatitude,
                                     String endPlaceLongitude);
}
