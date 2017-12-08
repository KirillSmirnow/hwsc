package org.smirnowku.hwsc.ui.signin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignInForm extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    private final TextField usernameField;
    private final PasswordField passwordField;

    public SignInForm() {
        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        Button signInButton = new Button("Sign In", clickEvent -> signIn());

        usernameField.setWidth(300, Unit.PIXELS);
        passwordField.setWidth(300, Unit.PIXELS);
        signInButton.setWidth(100, Unit.PIXELS);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(usernameField, passwordField, signInButton);
    }

    public void refresh() {
        usernameField.clear();
        passwordField.clear();
    }

    private void signIn() {
        try {
            signIn(usernameField.getValue(), passwordField.getValue());
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
        }
    }

    private void signIn(String username, String password) {
        authenticationService.signIn(username, password);
        UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
    }
}
