package dev.steady.global.auth;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Component
@RequestScope
public class AuthContext {

    private Long userId;

    public void registerAuth(Authentication authentication) {
        this.userId = authentication.userId();
    }

}

