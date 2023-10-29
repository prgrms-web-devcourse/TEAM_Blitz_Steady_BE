package dev.steady.auth.oauth.client;

import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.dto.response.OAuthUserInfoResponse;

public interface OAuthClient {

    Platform getPlatform();

    String getAccessToken(String authCode);

    OAuthUserInfoResponse getPlatformUserInfo(String accessToken);

}
