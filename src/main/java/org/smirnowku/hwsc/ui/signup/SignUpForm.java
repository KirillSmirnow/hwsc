package org.smirnowku.hwsc.ui.signup;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smirnowku.hwsc.core.service.impl.UserService;
import org.smirnowku.hwsc.dto.UserDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignUpForm extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(SignUpForm.class);

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private UserService userService;

    private TextField usernameField;
    private PasswordField passwordField;
    private PasswordField repeatPasswordField;
    private Button signUpButton;

    public SignUpForm() {
        usernameField = new TextField("Username:");
        passwordField = new PasswordField("Password:");
        repeatPasswordField = new PasswordField("Repeat password:");
        signUpButton = new Button("Sign Up", clickEvent -> signUp());
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
    }

    @PostConstruct
    public void init() {
        addComponents(usernameField, passwordField, repeatPasswordField, signUpButton);
    }

    public void refresh() {
        usernameField.clear();
        passwordField.clear();
        repeatPasswordField.clear();
    }

    private void signUp() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();
        String repeatedPassword = repeatPasswordField.getValue();
        if (!password.equals(repeatedPassword)) {
            passwordField.clear();
            repeatPasswordField.clear();
            Notification.show("Passwords don't match", Notification.Type.WARNING_MESSAGE);
        } else {
            UserDto dto = new UserDto(username, password, "Empty name");
            userService.signUp(dto);
            log.info(String.format("Sign up successful for user %s", username));
            authenticationService.signIn(username, password);
            UI.getCurrent().getNavigator().navigateTo(Views.ROOT);
            Notification.show(String.format("Signed in as %s", username), Notification.Type.TRAY_NOTIFICATION);
        }
    }

    private void signUpFail(String errorMessage) {
        log.info(String.format("Sign up failed: %s", errorMessage));
        Notification.show(errorMessage, Notification.Type.WARNING_MESSAGE);
    }
}
