package dev.steady.oauth.client;

import dev.steady.oauth.domain.Platform;
import dev.steady.oauth.dto.response.OAuthUserInfoResponse;

public interface OAuthClient {

    Platform getPlatform();

    String getAccessToken(String authCode);

    OAuthUserInfoResponse getPlatformUserInfo(String accessToken);

}
