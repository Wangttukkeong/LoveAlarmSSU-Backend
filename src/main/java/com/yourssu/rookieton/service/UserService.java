package com.yourssu.rookieton.service;

import com.yourssu.rookieton.dto.request.CreateInterestRequest;
import com.yourssu.rookieton.dto.request.CreateUserRequest;
import com.yourssu.rookieton.dto.response.UserResponse;
import com.yourssu.rookieton.dto.request.UpdateUserRequest;
import com.yourssu.rookieton.entity.User;
import com.yourssu.rookieton.entity.UserLocation;
import com.yourssu.rookieton.exception.CustomException;
import com.yourssu.rookieton.exception.ErrorCode;
import com.yourssu.rookieton.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LocationService locationService;

    public UserResponse getUserInfo(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse join(CreateUserRequest dto) {
        if (userRepository.existsById(dto.getId())) {
            throw new CustomException(ErrorCode.DUPLICATE_USER);
        }
        if (dto.getInterests() == null || dto.getInterests().isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_INTEREST);
        }

        User user = dto.toEntity();
        for (CreateInterestRequest interest : dto.getInterests()) {
            if (interest.getSubCategory() == null) {
                throw new CustomException(ErrorCode.EMPTY_INTEREST_SUBCATEGORY);
            }
            user.getInterestList().add(interest.toEntity(user));
        }

        if (dto.getLocation() != null) {
            UserLocation userLocation = UserLocation.builder()
                    .user(user)
                    .latitude(dto.getLocation().getLatitude())
                    .longitude(dto.getLocation().getLongitude())
                    .build();
            user.setUserLocation(userLocation);
        }
        User saved = userRepository.save(user);

        // redis 에 위치 저장
        locationService.updateLocation(saved.getId(), dto.getLocation().getLatitude(), dto.getLocation().getLongitude());
        return UserResponse.from(saved);
    }

    public void remove(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Transactional
    public ResponseEntity<Void> updateProfile(UUID userid, UpdateUserRequest dto) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateProfile(dto.getNickname(), dto.getPhoneNumber(), dto.getGender(),
                            dto.getBirthdate(), dto.getHeight(), dto.getDepartment(),
                            dto.getEmoji());
        user.updateAll(user);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
