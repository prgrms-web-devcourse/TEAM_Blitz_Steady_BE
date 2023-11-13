package dev.steady.auth.domain;

import dev.steady.global.exception.InvalidValueException;

import static dev.steady.auth.exception.AuthErrorCode.NOT_SUPPORTED_PLATFORM;

public enum Platform {

    KAKAO;

    public static Platform from(String platformName) {
        try {
            return Platform.valueOf(platformName.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidValueException(NOT_SUPPORTED_PLATFORM);
        }
    }

}
