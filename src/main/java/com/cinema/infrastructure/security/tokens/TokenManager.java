package com.cinema.infrastructure.security.tokens;

import com.cinema.domain.model.User;
import com.cinema.domain.repository.UserRepository;
import com.cinema.infrastructure.dto.RefreshTokenDto;
import com.cinema.infrastructure.dto.TokensDto;
import com.cinema.infrastructure.security.exceptions.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public TokensDto generateTokens(Authentication authentication) {
        if (Objects.isNull(authentication)) {
            throw new TokenException("AUTHENTICATION OBJECT IS NULL");
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

        return TokensDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //parsowanie
    public UsernamePasswordAuthenticationToken parse(String token) {

        if (Objects.isNull(token)) {
            throw new TokenException("token is null");
        }

        if (!token.startsWith(tokenPrefix)) {
            throw new TokenException("token is not correct");
        }

        var accessToken = token.replace(tokenPrefix, "");

        if (!isTokenValid(accessToken)) {
            throw new TokenException("token is not valid");
        }

        Long id = getId(accessToken);
        User user = userRepository
                .findOne(id)
                .orElseThrow(() -> new TokenException("parse token - cannot find user"));

        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                user
                        .getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                        .collect(Collectors.toList()));
    }
    public TokensDto parseFromRefreshToken(RefreshTokenDto refreshTokenDto) {

        String refreshToken = refreshTokenDto.getToken();

        if (Objects.isNull(refreshToken)) {
            throw new TokenException("token is null");
        }

        if (!isTokenValid(refreshToken)) {
            throw new TokenException("token is not valid");
        }

        Long accessTokenTimeMs = Long.parseLong(getClaims(refreshToken).get(accessTokenExpirationTimeProperty).toString());

        if (accessTokenTimeMs < System.currentTimeMillis()) {
            throw new TokenException("access token has been expired");
        }

        Long id = getId(refreshToken);
        User user = userRepository
                .findOne(id)
                .orElseThrow(() -> new TokenException("parse token - cannot find user"));

        Long accessTokenExpirationTimeInMillis = System.currentTimeMillis() + accessTokenExpirationTime;
        Date accessTokenExpirationDate = new Date(accessTokenExpirationTimeInMillis);
        Date refreshTokenExpirationDate = getExpiration(refreshToken);
        Date creationDate = new Date();

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(creationDate)
                .signWith(secretKey)
                .compact();

        String newRefreshToken = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(creationDate)
                .claim(accessTokenExpirationTimeProperty, accessTokenExpirationTimeInMillis)
                .signWith(secretKey)
                .compact();

        return TokensDto.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
    private Claims getClaims(String token) {
        if (token == null) {
            throw new TokenException("token is null");
        }

        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token.replace(tokenPrefix, ""))
                .getBody();
    }

    private Long getId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    private boolean isTokenValid(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }
    private Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }
}

