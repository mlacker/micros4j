package com.lacker.micros.auth.security.auth.token;

import io.jsonwebtoken.lang.Assert;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SkipPathRequestMatcher implements RequestMatcher {

    private OrRequestMatcher matcher;

    public SkipPathRequestMatcher(String ...paths) {
        Assert.notNull(paths, "Paths cannot be null");
        matcher = new OrRequestMatcher(Arrays.stream(paths).map(AntPathRequestMatcher::new).collect(Collectors.toList()));
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return matcher.matches(request);
    }
}
