package dev.steady.auth.dto.request;

import dev.steady.auth.domain.Account;
import dev.steady.oauth.domain.Platform;
import dev.steady.oauth.dto.response.OAuthUserInfoResponse;

public record AccountCreateRequest(
        Platform platform,
        String platformId
) {

    public static Account toEntity(Platform platform, String platformId){
        return Account.builder()
                .platform(platform)
                .platformId(platformId)
                .build();
    }

    public static AccountCreateRequest from(OAuthUserInfoResponse oAuthUserInfoResponse){
        return new AccountCreateRequest(oAuthUserInfoResponse.getPlatform(), oAuthUserInfoResponse.getPlatformId());
    }

}
