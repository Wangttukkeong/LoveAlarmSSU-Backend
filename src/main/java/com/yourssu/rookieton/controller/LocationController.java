package com.yourssu.rookieton.controller;

import com.yourssu.rookieton.dto.response.LocationRespose;
import com.yourssu.rookieton.dto.request.UpdateLocationRequest;
import com.yourssu.rookieton.dto.response.NearByUserResponse;
import com.yourssu.rookieton.service.LocationService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<LocationRespose> getLocation(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(locationService.getLocation(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateLocation(
            @PathVariable UUID id,
            @RequestBody UpdateLocationRequest dto) {
        locationService.updateLocation(id, dto.getLatitude(), dto.getLongitude());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nearby/all/{id}")
    public List<NearByUserResponse> all(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable UUID id) {
        return locationService.findNearByAll(id);
    }

    @GetMapping("/nearby/match/{id}")
    public List<NearByUserResponse> match(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable UUID id) {
        return locationService.findNearByWithCommonInterest(id);
    }

}
