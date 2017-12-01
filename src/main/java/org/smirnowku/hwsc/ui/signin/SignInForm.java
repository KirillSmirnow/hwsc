package org.smirnowku.hwsc.ui.signin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smirnowku.hwsc.core.service.impl.UserService;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignInForm extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(SignInForm.class);

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button signInButton;

    public SignInForm() {
        usernameField = new TextField("Username:");
        passwordField = new PasswordField("Password:");
        signInButton = new Button("Sign In", clickEvent -> signIn());
    }

    @PostConstruct
    public void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(usernameField, passwordField, signInButton);
    }

    public void refresh() {
        usernameField.clear();
        passwordField.clear();
    }

    private void signIn() {
        authenticationService.signIn(usernameField.getValue(), passwordField.getValue());
        Notification.show(String.format("Signed in as %s", authenticationService.getUser().getName()),
                Notification.Type.TRAY_NOTIFICATION);
    }

    private void signInFail(String errorMessage) {
        log.info(String.format("Sign in failed: %s", errorMessage));
        Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
    }
}
