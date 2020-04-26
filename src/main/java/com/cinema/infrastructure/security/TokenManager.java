package com.cinema.infrastructure.security;

import com.cinema.domain.model.User;
import com.cinema.domain.repository.UserRepository;
import com.cinema.infrastructure.dto.Tokens;
import com.cinema.infrastructure.exceptions.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenManager {
    private final SecretKey secretKey;
    private final UserRepository userRepository;

    @Value("${security.tokens.access-token.expiration-time}")
    private Long accessTokenExpirationTime;

    @Value("${security.tokens.refresh-token.expiration-time}")
    private Long refreshTokenExpirationTime;

    @Value("${security.tokens.refresh-token.access-token-expiration-time}")
    private String accessTokenExpirationTimeProperty;

    @Value("${security.tokens.token.prefix}")
    private String tokenPrefix;

    // @Value(" security.tokens.token.header=Authorization")

    public Tokens generateTokens(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            throw new SecurityException("AUTHENTICATION OBJECT IS NULL");
        }
        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new SecurityException("CANNOT FIND USER"));
        Long accessTokenExpirationTimeInMillis = System.currentTimeMillis() + accessTokenExpirationTime;
        Date accessTokenExpirationDate = new Date(System.currentTimeMillis() + accessTokenExpirationTime);
        Date refreshTokenExpirationDate = new Date(System.currentTimeMillis() + refreshTokenExpirationTime);
        Date creationDate = new Date();

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(creationDate)
                .signWith(secretKey)
                .compact();
        String refreshToken = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(creationDate)
                .signWith(secretKey)
                .claim(accessTokenExpirationTimeProperty, accessTokenExpirationTimeInMillis)
                .compact();

        return Tokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Claims getClaims(String token) {
        if (token == null) {
            throw new TokenException("TOKEN IS NULL");
        }
        if (!token.startsWith(tokenPrefix)) {
            throw new TokenException("TOKEN IS NOT CORRECT");
        }
        return Jwts
                .parserBuilder()
                .build()
                .parseClaimsJws(token.replace(tokenPrefix, ""))
                .getBody();
    }
}

