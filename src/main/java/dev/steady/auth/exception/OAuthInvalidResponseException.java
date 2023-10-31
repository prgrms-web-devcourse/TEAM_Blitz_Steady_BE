package dev.steady.auth.exception;

public class OAuthInvalidResponseException extends RuntimeException {

    private String message;

    public OAuthInvalidResponseException(String message) {
        this.message = message;
    }

}
