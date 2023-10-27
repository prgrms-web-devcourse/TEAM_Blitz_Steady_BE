package dev.steady.oauth.presentation;

import dev.steady.oauth.domain.Platform;
import dev.steady.oauth.dto.response.LogInResponse;
import dev.steady.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{oAuthProvider}/callback")
    public ResponseEntity<LogInResponse> logIn(@PathVariable String oAuthProvider,
                                               @RequestParam String code) {

        LogInResponse response = oAuthService.logIn(Platform.from(oAuthProvider), code);

        return ResponseEntity.ok(response);
    }

}
