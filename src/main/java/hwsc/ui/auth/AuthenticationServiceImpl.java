package hwsc.ui.auth;

import com.vaadin.server.VaadinSession;
import hwsc.HwscException;
import hwsc.dto.UserDto;
import hwsc.service.AuthService;
import hwsc.service.UserService;
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
            throw new HwscException("Incorrect username or password");
        }
    }

    @Override
    public void signOut() {
        VaadinSession.getCurrent().setAttribute(UserDto.class, null);
    }
}
