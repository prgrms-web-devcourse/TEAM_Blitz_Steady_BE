package dev.steady.global.auth;

import dev.steady.auth.domain.Authentication;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class AuthContext {

    private Long userId;

    public void registerAuth(Authentication authentication) {
        this.userId = authentication.userId();
    }

}
