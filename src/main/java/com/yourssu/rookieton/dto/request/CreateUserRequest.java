package com.yourssu.rookieton.dto.request;

import com.yourssu.rookieton.entity.User;
import com.yourssu.rookieton.entity.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    @NotBlank
    private UUID id;
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    private String phoneNumber;
    @NotBlank
    private String emoji;
    @NotBlank
    private String nickname;
    @NotBlank
    private Gender gender;
    @NotBlank
    private LocalDate birthdate;
    private Integer height;
    private String department;

    private List<CreateInterestRequest> interests;
    private CreateLocationRequest location;

    public User toEntity() {
        return User.builder()
                .id(id)
                .phoneNumber(phoneNumber)
                .emoji(emoji)
                .nickname(nickname)
                .gender(gender)
                .birthdate(birthdate)
                .height(height)
                .department(department)
                .interestList(new ArrayList<>())
                .userLocation(location.toEntity())
                .build();
    }
}
