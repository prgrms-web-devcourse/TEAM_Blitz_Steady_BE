package dev.steady.oauth.dto.response;

import dev.steady.oauth.domain.Platform;

public interface OAuthUserInfoResponse {

    Platform getPlatform();

    String getPlatformId();

}
