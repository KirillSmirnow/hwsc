package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.exception.ConflictException;
import org.smirnowku.hwsc.server.exception.NotFoundException;
import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.UserDto;
import org.smirnowku.hwsc.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public void signUp(UserDto dto) {
        if (userRepository.exists(dto.getUsername()))
            throw new ConflictException(String.format("Username %s occupied", dto.getUsername()));
        User user = new User(dto.getUsername(), dto.getPassword(), dto.getName());
        userRepository.save(user);
    }

    public void edit(long id, UserDto dto) {
        User user = get(id);
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        userRepository.save(user);
    }

    public User get(long id) {
        User user = userRepository.findOne(id);
        if (user == null) throw new NotFoundException("User not found");
        return user;
    }
}
