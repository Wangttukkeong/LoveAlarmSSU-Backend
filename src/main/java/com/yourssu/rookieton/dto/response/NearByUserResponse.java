package com.yourssu.rookieton.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class NearByUserResponse {
    @Schema(description = "사용자 ID")
    private UUID id;
    @Schema(description = "사용자 이름")
    private String nickname;
    @Schema(description = "사용자 이모지")
    private String emoji;
    @Schema(description = "사용자 관심사")
    private List<InterestResponse> interests;
    @Schema(description = "위도")
    private double latitude;
    @Schema(description = "경도")
    private double longitude;
    @Schema(description = "거리")
    private double distance;
}
