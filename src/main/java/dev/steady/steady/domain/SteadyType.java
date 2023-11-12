package dev.steady.steady.domain;

public enum SteadyType {

    PROJECT,
    STUDY;

    public static SteadyType from(String steadyType) {
        return SteadyType.valueOf(steadyType.toUpperCase());
    }

}
