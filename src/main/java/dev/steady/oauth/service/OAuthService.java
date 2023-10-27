package dev.steady.oauth.service;

import dev.steady.auth.domain.JwtProvider;
import dev.steady.auth.dto.request.AccountCreateRequest;
import dev.steady.auth.dto.request.TokenRequest;
import dev.steady.auth.dto.response.AccountResponse;
import dev.steady.auth.dto.response.TokenResponse;
import dev.steady.auth.service.AccountService;
import dev.steady.oauth.domain.Platform;
import dev.steady.oauth.dto.response.LogInResponse;
import dev.steady.oauth.dto.response.OAuthUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AuthCodeRequestService authCodeRequestService;
    private final OAuthClientService oAuthClientService;
    private final AccountService accountService;
    private final JwtProvider jwtProvider;

    public String getAuthCodeRequestUrlProvider(Platform platform) {
        return authCodeRequestService.provideRequestUrl(platform);
    }

    public LogInResponse logIn(Platform platform, String authCode) {
        OAuthUserInfoResponse userInfo = oAuthClientService.getUserInfo(platform, authCode);

        try {
            AccountResponse accountResponse = accountService.findByPlatformAndPlatformId(userInfo.getPlatform(), userInfo.getPlatformId());
            if (accountResponse.user() == null) {
                return LogInResponse.of(accountResponse.id(), true);
            } else {
                TokenRequest tokenRequest = TokenRequest.from(accountResponse);
                String accessToken = jwtProvider.provideAccessToken(tokenRequest);

                // TODO: 2023-10-26 리프레시 토큰 생성 로직 추가

                TokenResponse token = TokenResponse.of(accessToken, "리프레시 토큰");
                return LogInResponse.of(accountResponse.id(), false, token);
            }
        } catch (Exception e) {
            Long accountId = signUp(userInfo);
            return LogInResponse.of(accountId, true);
        }
    }

    public Long signUp(OAuthUserInfoResponse userInfo) {
        AccountCreateRequest request = AccountCreateRequest.from(userInfo);
        return accountService.create(request);
    }

}
