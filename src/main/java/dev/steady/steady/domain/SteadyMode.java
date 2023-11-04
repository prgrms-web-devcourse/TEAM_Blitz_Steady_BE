package dev.steady.steady.domain;

public enum SteadyMode {

    ONLINE,
    OFFLINE,
    BOTH;

    public static SteadyMode from(String mode) {
        return SteadyMode.valueOf(mode.toUpperCase());
    }

}
