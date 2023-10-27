package dev.steady.user.presentation;

import dev.steady.user.dto.request.UserCreateRequest;
import dev.steady.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/profile/{accountId}")
    public Long createUser(@PathVariable Long accountId, @RequestBody UserCreateRequest request) {
        return userService.createUser(accountId, request);

    }

}
