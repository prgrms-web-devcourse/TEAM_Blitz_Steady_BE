package dev.steady.oauth.service;

import dev.steady.oauth.domain.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AuthCodeRequestService authCodeRequestService;

    public String getAuthCodeRequestUrlProvider(Platform platform) {
        return authCodeRequestService.provideRequestUrl(platform);
    }

}
