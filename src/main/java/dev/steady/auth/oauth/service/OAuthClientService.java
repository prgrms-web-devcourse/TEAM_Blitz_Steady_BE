package dev.steady.auth.oauth.service;

import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.client.OAuthClient;
import dev.steady.auth.oauth.dto.response.OAuthUserInfoResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OAuthClientService {

    private final Map<Platform, OAuthClient> clientMap;

    public OAuthClientService(Set<OAuthClient> clients) {
        this.clientMap = clients.stream()
                .collect(Collectors.toUnmodifiableMap(
                        OAuthClient::getPlatform,
                        Function.identity()
                ));
    }

    public OAuthUserInfoResponse getUserInfo(Platform platform, String authCode) {
        OAuthClient oAuthClient = clientMap.get(platform);
        String accessToken = oAuthClient.getAccessToken(authCode);
        return oAuthClient.getPlatformUserInfo(accessToken);
    }

}
