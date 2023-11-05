package dev.steady.global.auth;

import dev.steady.auth.domain.Authentication;
import dev.steady.auth.domain.JwtResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtResolver jwtResolver;
    private final AuthContext authContext;

    @Autowired
    public JwtAuthenticationInterceptor(JwtResolver jwtResolver, AuthContext authContext) {
        this.authContext = authContext;
        this.jwtResolver = jwtResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod))
            return true;

        if (isAuthNotAnnotated(handlerMethod)) {
            return true;
        }

        String accessToken = getValueFromHeader(request, AUTHORIZATION);

        if (accessToken == null && !isAuthRequired(handlerMethod)) {
            return true;
        }

        jwtResolver.validateToken(accessToken);
        Authentication authentication = jwtResolver.getAuthentication(accessToken);
        authContext.registerAuth(authentication);

        return true;
    }

    private boolean isAuthNotAnnotated(HandlerMethod method) {
        MethodParameter[] methodParameters = method.getMethodParameters();
        return Arrays.stream(methodParameters)
                .noneMatch(parameter -> parameter.getParameterType().isAssignableFrom(UserInfo.class)
                                        && parameter.hasParameterAnnotation(Auth.class));
    }

    private boolean isAuthRequired(HandlerMethod method) {
        MethodParameter[] methodParameters = method.getMethodParameters();
        return Arrays.stream(methodParameters)
                .map(parameter -> parameter.getParameterAnnotation(Auth.class))
                .filter(Objects::nonNull)
                .findFirst()
                .filter(Auth::required)
                .isPresent();
    }

    private String getValueFromHeader(HttpServletRequest request, String headerName) {
        return request.getHeader(headerName);
    }

}

