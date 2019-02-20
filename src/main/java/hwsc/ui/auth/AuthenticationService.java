package hwsc.ui.auth;

import hwsc.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    boolean isAuthenticated();

    UserDto getUser();

    void signIn(String username, String password);

    void signOut();

    default String getUsername() {
        return getUser().getUsername();
    }
}
