package dev.steady.auth.fixture;

import dev.steady.auth.dto.response.TokenResponse;
import dev.steady.auth.oauth.dto.response.LogInResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class OAuthFixture {

    public static String createAuthCode() {
        return "SocialLoginAuthorizationCode";
    }

    public static LogInResponse createLogInResponseForUserNotExist() {
        return new LogInResponse(1L, true);
    }

    public static LogInResponse createLogInResponseForUserExist() {
        TokenResponse tokenResponse = new TokenResponse("액세스 토큰", "리프레시 토큰");
        return new LogInResponse(1L, false, tokenResponse);
    }

    public static URI createAuthCodeRequestUrl() {
        return UriComponentsBuilder
                .fromUriString("www.example.com/oauth/authorize")
                .queryParam("client_id", "{clientId}")
                .queryParam("redirect_uri", "{redirectUri}")
                .queryParam("response_type", "code")
                .build().toUri();
    }
}
