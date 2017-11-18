package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.User;
import org.smirnowku.hwsc.server.model.dto.UserDto;
import org.smirnowku.hwsc.server.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User getUser(long id) {
        return userRepository.findOne(id);
    }

    public void signUp(UserDto dto) {
        User user = new User(dto.name);
        userRepository.save(user);
    }
}
