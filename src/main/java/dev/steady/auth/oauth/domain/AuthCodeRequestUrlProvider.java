package dev.steady.auth.oauth.domain;

public interface AuthCodeRequestUrlProvider {

    Platform platform();

    String provideUrl();

}
