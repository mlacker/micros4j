package com.lacker.micros.auth.service;

import com.lacker.micros.auth.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username);
    }

    public User findByUsername(String username) {
        if (!"lacker".equals(username)) {
            throw new UsernameNotFoundException("user not found.");
        }

        User lacker = new User();
        lacker.setId("1");
        lacker.setUsername("lacker");
        lacker.setPassword("f123456");

        return lacker;
    }
}
