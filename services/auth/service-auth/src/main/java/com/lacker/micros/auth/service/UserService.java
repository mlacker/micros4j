package com.lacker.micros.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lacker.micros.auth.domain.User;
import com.lacker.micros.auth.model.LoginModel;
import com.lacker.micros.config.properties.TokenProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class UserService {

    private final TokenProperties properties;

    public UserService(TokenProperties properties) {
        this.properties = properties;
    }

    private User findByUsername(String username) {
        if (!"lacker".equals(username)) {
            throw new IllegalArgumentException("user not found.");
        }

        User lacker = new User();
        lacker.setId("1");
        lacker.setUsername("lacker");
        lacker.setPassword("f123456");

        return lacker;
    }

    public String login(LoginModel model) {
        if (StringUtils.isEmpty(model.getUsername()) || StringUtils.isEmpty(model.getPassword())) {
            throw new IllegalArgumentException("Username or password not provided");
        }

        User user = findByUsername(model.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + model.getUsername());
        }

        if (!user.getPassword().equals(model.getPassword())) {
            throw new IllegalArgumentException("Authentication failed. Username or password not valid.");
        }

        return createToken(user.getId());
    }

    private String createToken(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("Cannot create token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        return JWT.create()
                .withIssuer(properties.getIssuer())
                .withIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(currentTime.plusMinutes(properties.getExpires())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .withSubject(userId)
                .withClaim("app", "micros")
                .sign(Algorithm.HMAC256(properties.getSigningKey()));
    }
}
