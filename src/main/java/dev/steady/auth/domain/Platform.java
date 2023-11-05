package dev.steady.auth.domain;

import java.util.Arrays;

public enum Platform {

    KAKAO;

    public static Platform from(String platformName) {
        return Arrays.stream(Platform.values())
                .filter(v -> v.name().equals(platformName.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("일치하는 플랫폼이 없습니다: %s", platformName)));
    }

}
