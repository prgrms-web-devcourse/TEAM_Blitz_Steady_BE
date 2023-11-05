package dev.steady.auth.domain;

import dev.steady.auth.config.JwtProperties;
import dev.steady.auth.exception.JwtInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtResolver {

    private static final String BEARER_TYPE = "Bearer ";
    private final SecretKey key;

    public JwtResolver(JwtProperties jwtProperties) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public Authentication getAuthentication(String bearerAccessToken) {
        String accessToken = removeBearerType(bearerAccessToken);
        Claims claims = parseClaims(accessToken);
        return Authentication.from(claims);
    }

    public void validateToken(String bearerToken) {
        String token = removeBearerType(bearerToken);
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (IllegalArgumentException exception) {
            throw new JwtInvalidException("토큰이 존재하지 않습니다.");
        }
    }

    public Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();
    }

    private String removeBearerType(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_TYPE)) {
            return token.substring(BEARER_TYPE.length());
        }
        return null;
    }

}
