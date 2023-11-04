package dev.steady.auth.exception;

public class OAuthInvalidResponseException extends RuntimeException {

    public OAuthInvalidResponseException(String message) {
        super(message);
    }

}
