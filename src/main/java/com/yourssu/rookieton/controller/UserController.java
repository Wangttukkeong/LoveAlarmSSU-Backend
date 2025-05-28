package com.yourssu.rookieton.controller;

import com.yourssu.rookieton.dto.request.CreateUserRequest;
import com.yourssu.rookieton.dto.response.UserResponse;
import com.yourssu.rookieton.dto.request.UpdateUserRequest;
import com.yourssu.rookieton.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse>
    signUp(@Valid @RequestBody CreateUserRequest req) {
        return ResponseEntity.ok(userService.join(req));
    }

    @DeleteMapping("/withdrawal/{id}")
    public ResponseEntity<Void> withdrawal(@PathVariable UUID id) {
         userService.remove(id);
         return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> updateProfile(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest req) {
        return userService.updateProfile(id, req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserInfo(
            @Parameter(description = "사용자 ID", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

}
