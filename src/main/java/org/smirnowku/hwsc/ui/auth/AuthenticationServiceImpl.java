package org.smirnowku.hwsc.ui.auth;

import com.vaadin.server.VaadinSession;
import org.smirnowku.hwsc.core.model.User;
import org.smirnowku.hwsc.core.service.impl.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserService userService;

    @Override
    public boolean isAuthenticated() {
        return VaadinSession.getCurrent().getAttribute(User.class) != null;
    }

    @Override
    public User getUser() {
        return VaadinSession.getCurrent().getAttribute(User.class);
    }

    @Override
    public void signIn(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            VaadinSession.getCurrent().setAttribute(User.class, userService.get(username));
        } else {
            throw new RuntimeException("Wrong password");
        }
    }

    @Override
    public void signOut() {
        VaadinSession.getCurrent().setAttribute(User.class, null);
    }
}
