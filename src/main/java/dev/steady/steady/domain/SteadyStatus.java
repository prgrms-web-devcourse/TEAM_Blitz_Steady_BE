package dev.steady.steady.domain;

import java.util.Arrays;

public enum SteadyStatus {

    RECRUITING,
    CLOSED,
    FINISHED;

    public static SteadyStatus from(String status) {
        return Arrays.stream(SteadyStatus.values())
                .filter(v -> v.name().equals(status.toUpperCase()))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

}
