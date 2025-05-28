package com.yourssu.rookieton.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_USER(CONFLICT, "이미 존재하는 유저입니다."),

    USER_NOT_FOUND(NOT_FOUND, "유저가 존재하지 않습니다."),
    ARTICLE_NOT_FOUND(NOT_FOUND, "게시글이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(NOT_FOUND, "댓글이 존재하지 않습니다."),

    INVALID_PASSWORD(UNAUTHORIZED, "유저 패스워드가 일치하지 않습니다."),

    MISMATCH_COMMENT_AUTHOR(FORBIDDEN, "댓글 작성자가 아닙니다."),
    MISMATCH_ARTICLE_AUTHOR(FORBIDDEN, "게시글 작성자가 아닙니다."),

    DUPLICATE_INTEREST_CATEGORY(CONFLICT, "중복된 카테고리가 들어왔습니다."),

    EMPTY_INTEREST(BAD_REQUEST, "관심사가 비어있습니다."),
    EMPTY_INTEREST_CATEGORY(BAD_REQUEST, "카테고리가 존재하지 않습니다."),
    EMPTY_INTEREST_SUBCATEGORY(BAD_REQUEST, "관심사가 존재하지 않습니다."),
    INVALID_INTEREST_CATEGORY(BAD_REQUEST, "올바른 카테고리가 아닙니다."),
    INVALID_INTEREST_SUBCATEGORY(BAD_REQUEST, "올바른 관심사가 아닙니다."),
    MISMATCH_CATEGORY(BAD_REQUEST, "카테고리와 관심사가 매치되지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
