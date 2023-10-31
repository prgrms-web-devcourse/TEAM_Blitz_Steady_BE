package dev.steady.auth.domain;

import dev.steady.auth.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final Key key;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String provideAccessToken(Account account) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + jwtProperties.getAccessTokenExpiration());

        return Jwts.builder()
                .subject(String.valueOf(account.getUser().getId()))
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(key)
                .compact();
    }

    // TODO: 2023-10-26 RefreshToken 생성 메서드 추가

}
