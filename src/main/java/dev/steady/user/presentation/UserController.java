package dev.steady.user.presentation;

import dev.steady.auth.domain.Platform;
import dev.steady.auth.oauth.service.OAuthService;
import dev.steady.auth.service.AccountService;
import dev.steady.user.dto.request.UserCreateRequest;
import dev.steady.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;
    private final OAuthService oAuthService;

    @PostMapping("/profile")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequest request) {
        Long userId = userService.createUser(request);
        Platform platform = accountService.registerUser(request.accountId(), userId);
        URI authCodeRequestUrl = oAuthService.getAuthCodeRequestUrlProvider(platform);

        return ResponseEntity.created(authCodeRequestUrl).build();
    }

    @GetMapping("/profile/exist")
    public ResponseEntity<Boolean> existsByNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(userService.existsByNickname(nickname));
    }

}
