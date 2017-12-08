package org.smirnowku.hwsc.rest;

import org.smirnowku.hwsc.core.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String passwordHash = authService.getPasswordHash(username);
        if (passwordHash == null) throw new UsernameNotFoundException(String.format("User %s not found", username));
        return new org.springframework.security.core.userdetails.User(username, passwordHash, Collections.emptySet());
    }
}
