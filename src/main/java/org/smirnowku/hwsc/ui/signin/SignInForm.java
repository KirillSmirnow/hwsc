package org.smirnowku.hwsc.ui.signin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignInForm extends VerticalLayout {

    private static final Logger log = LoggerFactory.getLogger(SignInForm.class);

    @Resource
    private AuthenticationService authenticationService;

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
        try {
            authenticationService.signIn(usernameField.getValue(), passwordField.getValue());
        } catch (BaseException e) {
            log.info(String.format("Sign in failed: %s", e.getMessage()));
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return;
        }
        UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        Notification.show(String.format("Signed in as %s", authenticationService.getUser().getName()),
                Notification.Type.TRAY_NOTIFICATION);
    }
}
