package org.smirnowku.hwsc.core.service.impl;

import org.smirnowku.hwsc.core.exception.ConflictException;
import org.smirnowku.hwsc.core.exception.NotFoundException;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.repository.UserRepository;
import org.smirnowku.hwsc.core.service.UserService;
import org.smirnowku.hwsc.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new ConflictException(String.format("Username %s occupied", dto.getUsername()));
        String password = passwordEncoder.encode(dto.password());
        User user = new User(dto.getUsername(), password, dto.getName());
        userRepository.save(user);
    }

    @Override
    public void edit(String username, UserDto dto) {
        User user = getEntity(username);
        String password = passwordEncoder.encode(dto.password());
        user.setPassword(password);
        user.setName(dto.getName());
        userRepository.save(user);
    }

    @Override
    public UserDto get(String username) {
        return getEntity(username).toDto();
    }

    @Override
    public User getEntity(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFoundException(String.format("User %s not found", username));
        return user;
    }
}
