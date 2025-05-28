package com.yourssu.rookieton.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yourssu.rookieton.exception.CustomException;
import com.yourssu.rookieton.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum CategoryType {
    // 음악 - 밴드, 힙합, 케이팝, 클래식
    BAND("밴드", Category.MUSIC),
    HIPHOP("힙합", Category.MUSIC),
    CLASSIC("클래식", Category.MUSIC),
    KPOP("케이팝", Category.MUSIC),

    // 미디어 - 영화, 드라마, 예능, 다큐멘터리
    MOVIE("영화", Category.MEDIA),
    DRAMA("드라마", Category.MEDIA),
    VARIETY("예능", Category.MEDIA),
    DOCUMENTARY("다큐멘터리", Category.MEDIA),

    // 게임 - 모바일게임, 콘솔게임, PC게임
    MOBILE("모바일게임", Category.GAME),
    CONSOLE("콘솔게임", Category.GAME),
    PC("PC게임", Category.GAME),

    // 운동 - 러닝, 헬스, 클라이밍, 복싱, 수영, 보드, 체조, 댄스
    RUNNING("러닝", Category.EXERCISE),
    GYM("헬스", Category.EXERCISE),
    CLIMBING("클라이밍", Category.EXERCISE),
    BOXING("복싱", Category.EXERCISE),
    SWIMMING("수영", Category.EXERCISE),
    BOARD("보드", Category.EXERCISE),
    GYMNASTICS("체조", Category.EXERCISE),
    DANCE("댄스", Category.EXERCISE),

    // 스포츠 - KBO, K리그, 해외축구, e스포츠, 농구, 배구, 모터스포츠
    KBO("KBO", Category.SPORTS),
    K_LEAGUE("K리그", Category.SPORTS),
    OVERSEAS_FOOTBALL("해외축구", Category.SPORTS),
    E_SPORTS("e스포츠", Category.SPORTS),
    BASKETBALL("농구", Category.SPORTS),
    VOLLEYBALL("배구", Category.SPORTS),
    MOTOR("모터스포츠", Category.SPORTS),

    // 독서 - 소설, 에세이, 시집, 웹소설, 자기계발서
    NOVEL("소설", Category.READING),
    ESSAY("에세이", Category.READING),
    POEM("시집", Category.READING),
    WEB_NOVEL("웹소설", Category.READING),
    SELF_DEVELOPMENT("자기계발서", Category.READING),

    // 패션 - 스트릿, 빈티지, 클래식, 수집
    STREET("스트릿", Category.FASHION),
    VINTAGE("빈티지", Category.FASHION),
    CLASSIC_FASHION("클래식", Category.FASHION),
    COLLECTION("수집", Category.FASHION),

    // 식도락 - 맛집탕방, 카페, 요리
    RESTAURANT("맛집탐방", Category.FOODIE),
    CAFE("카페", Category.FOODIE),
    COOKING("요리", Category.FOODIE),

    // 여행 - 국내여행, 해외여행, 캠핑
    DOMESTIC("국내여행", Category.TRAVEL),
    OVERSEAS("해외여행", Category.TRAVEL),
    CAMPING("캠핑", Category.TRAVEL),
    ;

    private final String value;
    private final Category parent;

    CategoryType(String value, Category parent) {
        this.value = value;
        this.parent = parent;
    }

    @JsonCreator
    public static CategoryType from(String name) {
        if (name == null || name.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_INTEREST_SUBCATEGORY);
        }
        for (CategoryType c : CategoryType.values()) {
            if (c.name().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new CustomException(ErrorCode.INVALID_INTEREST_SUBCATEGORY);
    }
}
