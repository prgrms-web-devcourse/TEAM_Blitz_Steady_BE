package dev.steady.steady.domain;

import java.util.Arrays;

public enum SteadyMode {

    ONLINE,
    OFFLINE,
    BOTH;

    public static SteadyMode from(String mode) {
        return Arrays.stream(SteadyMode.values())
                .filter(v -> v.name().equals(mode.toUpperCase()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
