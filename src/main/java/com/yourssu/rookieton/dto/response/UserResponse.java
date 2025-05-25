package com.yourssu.rookieton.dto.response;

import com.yourssu.rookieton.entity.User;
import com.yourssu.rookieton.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String phoneNumber;
    private String nickname;
    private Gender gender;
    private LocalDate birthdate;
    private Integer height;
    private String department;
    private List<InterestResponse> interestList;
    private LocationRespose userLocation;

    public static UserResponse from(User user) {
        List<InterestResponse> interestList = user.getInterestList().stream()
                .map(interest -> new InterestResponse(interest.getSubCategory(), interest.getHashtagList()))
                .toList();

        LocationRespose userLocation = LocationRespose.from(user.getUserLocation());

        return new UserResponse(
                user.getPhoneNumber(),
                user.getNickname(),
                user.getGender(),
                user.getBirthdate(),
                user.getHeight(),
                user.getDepartment(),
                interestList,
                userLocation
        );
    }
}
