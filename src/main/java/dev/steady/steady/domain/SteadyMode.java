package dev.steady.steady.domain;

import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor
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
