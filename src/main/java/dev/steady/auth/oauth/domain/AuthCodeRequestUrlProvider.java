package dev.steady.auth.oauth.domain;

import dev.steady.auth.domain.Platform;

public interface AuthCodeRequestUrlProvider {

    Platform platform();

    String provideUrl();

}
