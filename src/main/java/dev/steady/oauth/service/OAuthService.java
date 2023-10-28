package dev.steady.oauth.service;

import dev.steady.auth.domain.Account;
import dev.steady.auth.domain.JwtProvider;
import dev.steady.auth.domain.repository.AccountRepository;
import dev.steady.auth.dto.request.TokenRequest;
import dev.steady.auth.dto.response.TokenResponse;
import dev.steady.oauth.domain.Platform;
import dev.steady.oauth.dto.response.LogInResponse;
import dev.steady.oauth.dto.response.OAuthUserInfoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AuthCodeRequestService authCodeRequestService;
    private final OAuthClientService oAuthClientService;
    private final AccountRepository accountRepository;
    private final JwtProvider jwtProvider;

    public String getAuthCodeRequestUrlProvider(Platform platform) {
        return authCodeRequestService.provideRequestUrl(platform);
    }

    public LogInResponse logIn(Platform platform, String authCode) {
        OAuthUserInfoResponse userInfo = oAuthClientService.getUserInfo(platform, authCode);

        try {
            Account account = accountRepository.findByPlatformAndPlatformId(userInfo.getPlatform(), userInfo.getPlatformId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("플랫폼 %s의 id %s에 해당하는 계정이 없습니다.", userInfo.getPlatform(), userInfo.getPlatformId())));
            if (account.getUser() == null) {
                return LogInResponse.forUserNotExist(account.getId());
            } else {
                TokenRequest tokenRequest = TokenRequest.from(account);
                String accessToken = jwtProvider.provideAccessToken(tokenRequest);

                // TODO: 2023-10-26 리프레시 토큰 생성 로직 추가

                TokenResponse token = TokenResponse.of(accessToken, "리프레시 토큰");
                return LogInResponse.forUserExist(account.getId(), token);
            }
        } catch (Exception e) {
            Long accountId = signUp(userInfo);
            return LogInResponse.forUserNotExist(accountId);
        }
    }

    public Long signUp(OAuthUserInfoResponse userInfo) {
        Account account = OAuthUserInfoResponse.toEntity(userInfo);
        return accountRepository.save(account).getId();
    }

}
