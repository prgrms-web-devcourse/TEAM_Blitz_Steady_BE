package dev.steady.review.exception;

import dev.steady.global.exception.ErrorCode;

public enum ReviewErrorCode implements ErrorCode {

    CARD_NOT_FOUND("R001", "카드를 찾을 수 없습니다."),
    STEADY_NOT_FINISHED("R002", "스테디가 종료되지 않았습니다."),
    REVIEWEE_EQUALS_REVIEWER("R003", "리뷰이와 리뷰어가 동일합니다."),
    REVIEW_DUPLICATE("R004", "리뷰를 중복 제출할 수 없습니다.");

    private final String code;
    private final String message;

    ReviewErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
