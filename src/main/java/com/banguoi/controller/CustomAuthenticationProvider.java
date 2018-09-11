package com.banguoi.controller;

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
    List<User> users = new ArrayList<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Iterable<User> userss = userService.findAll();
        for (User u : userss) {
            users.add(u);
        }

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> optionalUser = users.stream().filter(u -> u.index(name, password)).findFirst();

        if (!optionalUser.isPresent()) {
            logger.error("Authentication failed for user = " + name);
            throw new BadCredentialsException("Authentication failed for user = " + name);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(optionalUser.get().getRole().getRoles()));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(name, password,
                grantedAuthorities);

        logger.info("Succesful Authentication with user = " + name);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
