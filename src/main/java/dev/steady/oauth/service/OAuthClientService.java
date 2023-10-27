package dev.steady.oauth.service;

import dev.steady.oauth.client.OAuthClient;
import dev.steady.oauth.domain.Platform;
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

}
