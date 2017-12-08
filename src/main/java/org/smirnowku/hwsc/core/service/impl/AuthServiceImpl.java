package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.UserRepository;
import org.smirnowku.hwsc.core.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean areCredentialsCorrect(String username, String password) {
        String passwordHash = getPasswordHash(username);
        return passwordHash != null && passwordEncoder.matches(password, passwordHash);
    }

    @Override
    public String getPasswordHash(String username) {
        User user = userRepository.findByUsername(username);
        return user == null ? null : user.getPassword();
    }
}
