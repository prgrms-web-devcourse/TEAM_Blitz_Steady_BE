package dev.steady.auth.oauth.client;

import dev.steady.auth.config.KakaoOAuthProperties;
import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.dto.KakaoToken;
import dev.steady.auth.oauth.dto.response.KakaoUserInfoResponse;
import dev.steady.auth.oauth.dto.response.OAuthUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String REQUEST_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String REQUEST_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final RestTemplate restTemplate;

    @Override
    public Platform getPlatform() {
        return Platform.KAKAO;
    }

    @Override
    public String getAccessToken(String authCode) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", kakaoOAuthProperties.getClientId());
        body.add("redirect_uri", kakaoOAuthProperties.getRedirectUri());
        body.add("client_secret", kakaoOAuthProperties.getClientSecret());
        body.add("code", authCode);

        KakaoToken token = restTemplate.postForObject(
                REQUEST_TOKEN_URL,
                new HttpEntity<>(body, httpHeaders),
                KakaoToken.class
        );

        if (token == null) throw new AssertionError("토큰을 발급 받을 수 없습니다.");
        return token.accessToken();
    }

    @Override
    public OAuthUserInfoResponse getPlatformUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBearerAuth(accessToken);

        KakaoUserInfoResponse response = restTemplate.postForObject(
                REQUEST_USER_INFO_URL,
                new HttpEntity<>(httpHeaders),
                KakaoUserInfoResponse.class
        );

        if (response == null) throw new AssertionError("사용자 정보를 가져올 수 없습니다.");
        return response;
    }

}
