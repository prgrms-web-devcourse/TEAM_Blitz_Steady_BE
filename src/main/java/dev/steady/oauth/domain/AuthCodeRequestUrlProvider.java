package dev.steady.oauth.domain;

public interface AuthCodeRequestUrlProvider {

    Platform platform();
    String provideUrl();
    
}
