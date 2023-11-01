package dev.steady.user.presentation;

import dev.steady.auth.service.AccountService;
import dev.steady.user.dto.request.UserCreateRequest;
import dev.steady.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @PostMapping("/profile")
    public Long createUser(@RequestBody UserCreateRequest request) {
        Long userId = userService.createUser(request);
        accountService.registerUser(request.accountId(), userId);

        return userId;
    }

    @GetMapping("/profile/exist")
    public Boolean existsByNickname(@RequestParam String nickname) {
        return userService.existsByNickname(nickname);
    }

}
