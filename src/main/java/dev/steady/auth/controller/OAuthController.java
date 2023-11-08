package dev.steady.auth.controller;

import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.dto.response.LogInResponse;
import dev.steady.auth.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/{oAuthProvider}")
    public ResponseEntity<Void> getAuthorizationCodeRequestUrl(@PathVariable String oAuthProvider) {
        URI url = oAuthService.getAuthCodeRequestUrlProvider(Platform.from(oAuthProvider));
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{oAuthProvider}/callback")
    public ResponseEntity<LogInResponse> logIn(@PathVariable String oAuthProvider, @RequestParam String code) {
        LogInResponse response = oAuthService.logIn(Platform.from(oAuthProvider), code);
        return ResponseEntity.ok(response);
    }

}
