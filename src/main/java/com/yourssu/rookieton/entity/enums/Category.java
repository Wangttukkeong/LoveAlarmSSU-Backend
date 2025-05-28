package com.yourssu.rookieton.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yourssu.rookieton.exception.CustomException;
import com.yourssu.rookieton.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum Category {
    MUSIC("음악"),
    MEDIA("미디어"),
    GAME("게임"),
    EXERCISE("운동"),
    SPORTS("스포츠"),
    READING("독서"),
    FASHION("패션"),
    FOODIE("식도락"),
    TRAVEL("여행"),
    ;
    private final String value;

    Category(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Category from(String name) {
        if (name == null || name.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_INTEREST_CATEGORY);
        }
        for (Category c : Category.values()) {
            if (c.name().equalsIgnoreCase(name)) {
                return c;
            }
        }
        throw new CustomException(ErrorCode.INVALID_INTEREST_CATEGORY);
    }

}
