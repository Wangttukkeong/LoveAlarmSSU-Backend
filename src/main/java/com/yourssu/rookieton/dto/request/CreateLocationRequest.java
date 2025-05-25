package com.yourssu.rookieton.dto.request;

import com.yourssu.rookieton.entity.UserLocation;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateLocationRequest {
    @NotBlank
    private double latitude;
    @NotBlank
    private double longitude;

    public UserLocation toEntity() {
        return UserLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
