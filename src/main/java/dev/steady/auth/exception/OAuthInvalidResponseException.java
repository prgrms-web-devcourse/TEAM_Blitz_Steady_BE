package dev.steady.auth.exception;

public class OAuthInvalidResponseException extends RuntimeException {

    private final String message;

    public OAuthInvalidResponseException(String message) {
        this.message = message;
    }

}
