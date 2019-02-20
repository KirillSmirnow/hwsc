package hwsc.service.impl;

import hwsc.HwscException;
import hwsc.dto.UserDto;
import hwsc.model.User;
import hwsc.repository.UserRepository;
import hwsc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserDto dto) {
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new HwscException(String.format("Username %s occupied", dto.getUsername()));
        String password = passwordEncoder.encode(dto.getPassword());
        User user = new User(dto.getUsername(), password, dto.getName());
        userRepository.save(user);
    }

    @Override
    public void edit(String username, UserDto dto) {
        User user = getEntity(username);
        String password = passwordEncoder.encode(dto.getPassword());
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
        if (user == null) throw new HwscException(String.format("User %s not found", username));
        return user;
    }
}
