package com.yourssu.rookieton.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateLocationRequest {
    @NotBlank
    private double latitude;
    @NotBlank
    private double longitude;
}
