package org.smirnowku.hwsc.service;

import org.smirnowku.hwsc.exception.ConflictException;
import org.smirnowku.hwsc.exception.NotFoundException;
import org.smirnowku.hwsc.model.User;
import org.smirnowku.hwsc.model.dto.UserDto;
import org.smirnowku.hwsc.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    public void signUp(UserDto dto) {
        if (userRepository.exists(dto.getUsername()))
            throw new ConflictException(String.format("Username %s occupied", dto.getUsername()));
        String password = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), password, dto.getName());
        userRepository.save(user);
    }

    public void edit(String username, UserDto dto) {
        User user = get(username);
        String password = passwordEncoder.encode(dto.getPassword());
        user.setPassword(password);
        user.setName(dto.getName());
        userRepository.save(user);
    }

    public User get(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new NotFoundException(String.format("User %s not found", username));
        return user;
    }
}
