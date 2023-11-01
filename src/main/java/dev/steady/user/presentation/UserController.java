package dev.steady.user.presentation;

import dev.steady.auth.service.AccountService;
import dev.steady.user.dto.request.CreateUserRequest;
import dev.steady.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @PostMapping("/profile")
    public Long createUser(@RequestBody CreateUserRequest request) {
        Long userId = userService.createUser(request);
        accountService.registerUser(request.accountId(), userId);

        return userId;
    }
    
}
