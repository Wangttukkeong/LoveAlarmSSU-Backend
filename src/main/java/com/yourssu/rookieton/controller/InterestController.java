package com.yourssu.rookieton.controller;

import com.yourssu.rookieton.dto.response.InterestResponse;
import com.yourssu.rookieton.dto.request.UpdateInterestRequest;
import com.yourssu.rookieton.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interest")
public class InterestController {
    private final InterestService interestService;

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateInterest(@PathVariable UUID id, @RequestBody List<UpdateInterestRequest> dtoList) {
        interestService.updateInterest(id, dtoList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<InterestResponse>> getInterests(@PathVariable UUID id) {
        List<InterestResponse> interests = interestService.getInterests(id);
        return ResponseEntity.ok(interests);
    }

}
