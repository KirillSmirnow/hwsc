package hwsc.ui.signup;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import hwsc.HwscException;
import hwsc.dto.UserDto;
import hwsc.service.UserService;
import hwsc.ui.Views;
import hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignUpForm extends VerticalLayout {

    private final TextField usernameField;
    private final TextField nameField;
    private final PasswordField passwordField;
    private final PasswordField repeatPasswordField;
    @Resource
    private AuthenticationService authenticationService;
    @Resource
    private UserService userService;

    public SignUpForm() {
        usernameField = new TextField("Username");
        nameField = new TextField("Name");
        passwordField = new PasswordField("Password");
        repeatPasswordField = new PasswordField("Confirm password");
        Button signUpButton = new Button("Sign Up", clickEvent -> signUp());

        usernameField.setWidth(300, Unit.PIXELS);
        nameField.setWidth(300, Unit.PIXELS);
        passwordField.setWidth(300, Unit.PIXELS);
        repeatPasswordField.setWidth(300, Unit.PIXELS);
        signUpButton.setWidth(100, Unit.PIXELS);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(usernameField, nameField, passwordField, repeatPasswordField, signUpButton);
    }

    public void refresh() {
        usernameField.clear();
        nameField.clear();
        passwordField.clear();
        repeatPasswordField.clear();
    }

    private void signUp() {
        String password = passwordField.getValue();
        String repeatedPassword = repeatPasswordField.getValue();
        if (password.equals(repeatedPassword)) {
            signUp(usernameField.getValue(), nameField.getValue(), password);
        } else {
            passwordField.clear();
            repeatPasswordField.clear();
            Notification.show("Passwords don't match", Notification.Type.WARNING_MESSAGE);
        }
    }

    private void signUp(String username, String name, String password) {
        try {
            UserDto dto = new UserDto(username, password, name);
            userService.signUp(dto);
            signIn(username, password);
        } catch (HwscException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
        }
    }

    private void signIn(String username, String password) {
        authenticationService.signIn(username, password);
        UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
    }
}
