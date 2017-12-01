package org.smirnowku.hwsc.rest.security;

import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(String.format("User %s not found", username));
        return new org.springframework.security.core.userdetails.User(username, user.password(), Collections.emptySet());
    }
}
