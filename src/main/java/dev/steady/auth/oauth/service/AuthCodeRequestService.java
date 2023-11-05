package dev.steady.auth.oauth.service;

import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.domain.AuthCodeRequestUrlProvider;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthCodeRequestService {

    private final Map<Platform, AuthCodeRequestUrlProvider> urlProviderMap;

    public AuthCodeRequestService(Set<AuthCodeRequestUrlProvider> urlProviders) {
        this.urlProviderMap = urlProviders.stream().collect(
                Collectors.toUnmodifiableMap(
                        AuthCodeRequestUrlProvider::platform,
                        Function.identity()
                )
        );
    }

    public URI provideRequestUrl(Platform platform) {
        return urlProviderMap.get(platform).provideUrl();
    }

}
