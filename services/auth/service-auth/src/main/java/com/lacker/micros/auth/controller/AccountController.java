package com.lacker.micros.auth.controller;

import com.lacker.micros.auth.domain.User;
import com.lacker.micros.auth.security.auth.token.extractor.TokenExtractor;
import com.lacker.micros.auth.security.auth.token.verifier.TokenVerifier;
import com.lacker.micros.auth.security.config.TokenProperties;
import com.lacker.micros.auth.security.config.WebSecurityConfigurer;
import com.lacker.micros.auth.security.exceptions.InvalidTokenException;
import com.lacker.micros.auth.security.model.token.RawAccessToken;
import com.lacker.micros.auth.security.model.token.RefreshToken;
import com.lacker.micros.auth.security.model.token.Token;
import com.lacker.micros.auth.security.model.token.TokenFactory;
import com.lacker.micros.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class AccountController {

    private final TokenProperties tokenProperties;
    private final TokenVerifier tokenVerifier;
    private final TokenFactory tokenFactory;
    private final TokenExtractor tokenExtractor;
    private final UserService userService;

    @Autowired
    public AccountController(
            TokenProperties tokenProperties,
            TokenVerifier tokenVerifier,
            TokenFactory tokenFactory,
            TokenExtractor tokenExtractor,
            UserService userService) {
        this.tokenProperties = tokenProperties;
        this.tokenVerifier = tokenVerifier;
        this.tokenFactory = tokenFactory;
        this.tokenExtractor = tokenExtractor;
        this.userService = userService;
    }

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("/api/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/api/manage/test3")
    public String test3() {
        return "test3";
    }

    @GetMapping("/api/auth/refresh_token")
    public Token refreshToken(HttpServletRequest request) {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfigurer.TOKEN_HEADER_PARAM));
        RawAccessToken rawToken = new RawAccessToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, tokenProperties.getSigningKey()).orElseThrow(() -> new InvalidTokenException("Token验证失败"));

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidTokenException("Token验证失败");
        }

        String subject = refreshToken.getSubject();
        User user = Optional.ofNullable(userService.findByUsername(subject)).orElseThrow(() -> new UsernameNotFoundException("用户未找到: " + subject));

        return tokenFactory.createAccessToken(user.getUsername());
    }
}
