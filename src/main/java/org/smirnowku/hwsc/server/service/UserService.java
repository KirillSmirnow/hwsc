package org.smirnowku.hwsc.server.service;

import org.smirnowku.hwsc.server.model.User;
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

    public void signUp() {
        User user = new User();
        userRepository.save(user);
    }
}
