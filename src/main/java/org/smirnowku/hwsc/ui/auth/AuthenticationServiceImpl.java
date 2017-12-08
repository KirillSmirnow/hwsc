package org.smirnowku.hwsc.ui.auth;

import com.vaadin.server.VaadinSession;
import org.smirnowku.hwsc.core.exception.ForbiddenException;
import org.smirnowku.hwsc.core.service.impl.AuthService;
import org.smirnowku.hwsc.core.service.impl.UserService;
import org.smirnowku.hwsc.dto.UserDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private AuthService authService;

    @Resource
    private UserService userService;

    @Override
    public boolean isAuthenticated() {
        return getUser() != null;
    }

    @Override
    public UserDto getUser() {
        return VaadinSession.getCurrent().getAttribute(UserDto.class);
    }

    @Override
    public void signIn(String username, String password) {
        if (authService.areCredentialsCorrect(username, password)) {
            VaadinSession.getCurrent().setAttribute(UserDto.class, userService.get(username));
        } else {
            throw new ForbiddenException("Incorrect username or password");
        }
    }

    @Override
    public void signOut() {
        VaadinSession.getCurrent().setAttribute(UserDto.class, null);
    }
}
