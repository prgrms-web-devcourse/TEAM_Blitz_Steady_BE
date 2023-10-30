package dev.steady.auth.presentation;

import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/{oAuthProvider}")
    public ResponseEntity<String> getAuthorizationCodeRequestUrl(@PathVariable String oAuthProvider) {
        String url = oAuthService.getAuthCodeRequestUrlProvider(Platform.from(oAuthProvider));
        return ResponseEntity.ok(url);
    }

}
