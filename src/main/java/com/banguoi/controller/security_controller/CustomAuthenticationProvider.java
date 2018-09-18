package com.banguoi.controller.security_controller;

import com.banguoi.model.User;
import com.banguoi.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<User> users = new ArrayList<>();

        Iterable<User> us = userService.findAll();
        for (User u : us) {
            users.add(u);
        }

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> optionalUser = users.stream().filter(u -> u.index(email, password)).findFirst();

        if (!optionalUser.isPresent()) {
            logger.error("Authentication failed for user = " + email);
            throw new BadCredentialsException("Authentication failed for user = " + email);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        optionalUser.get().setRole(userService.findUserByEmail(email).getRole());
        grantedAuthorities.add(new SimpleGrantedAuthority(optionalUser.get().getRole().getRoles()));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password,
                grantedAuthorities);

        logger.info("Successfully Authentication with user = " + email);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
