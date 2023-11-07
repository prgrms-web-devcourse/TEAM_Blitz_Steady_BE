package dev.steady.global.exception;

public enum GlobalErrorCode implements ErrorCode {

    INTERNAL_SERVER_ERROR("G100", "서버에 알 수 없는 문제가 발생했습니다.");

    private final String code;
    private final String message;

    GlobalErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

}
