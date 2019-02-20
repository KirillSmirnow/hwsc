package hwsc.service.impl;

import hwsc.model.User;
import hwsc.repository.UserRepository;
import hwsc.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
