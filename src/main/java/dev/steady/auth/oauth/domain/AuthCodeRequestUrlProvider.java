package dev.steady.auth.oauth.domain;

import dev.steady.auth.domain.Platform;

import java.net.URI;

public interface AuthCodeRequestUrlProvider {

    Platform platform();

    URI provideUrl();

}
