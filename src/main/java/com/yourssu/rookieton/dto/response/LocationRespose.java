package com.yourssu.rookieton.dto.response;

import com.yourssu.rookieton.entity.UserLocation;
import lombok.Data;

@Data
public class LocationRespose {
    private double latitude;
    private double longitude;

    public static LocationRespose from(UserLocation userLocation) {
        LocationRespose locationRespose = new LocationRespose();
        locationRespose.setLatitude(userLocation.getLatitude());
        locationRespose.setLongitude(userLocation.getLongitude());
        return locationRespose;
    }
}
