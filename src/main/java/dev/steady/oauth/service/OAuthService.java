package dev.steady.oauth.service;

import dev.steady.oauth.domain.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AuthCodeRequestService authCodeRequestService;
    private final OAuthClientService oAuthClientService;
    private final AccountService accountService;

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
                // TODO: 2023-10-25 서비스 JWT 발급 로직 추가
                return null;
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
