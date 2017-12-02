package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    public boolean areCredentialsCorrect(String username, String password) {
        String passwordHash = getPasswordHash(username);
        return passwordHash != null && passwordEncoder.matches(password, passwordHash);
    }

    public String getPasswordHash(String username) {
        User user = userRepository.findByUsername(username);
        return user == null ? null : user.getPassword();
    }
}
