package org.smirnowku.hwsc.ui.auth;

import org.smirnowku.hwsc.core.model.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    boolean isAuthenticated();

    User getUser();

    void signIn(String username, String password);

    void signOut();
}
