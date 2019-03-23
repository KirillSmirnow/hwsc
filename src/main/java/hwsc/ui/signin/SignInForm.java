package hwsc.ui.signin;

import com.vaadin.event.ShortcutAction;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import hwsc.HwscException;
import hwsc.ui.Views;
import hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class SignInForm extends VerticalLayout {

    private final TextField usernameField;
    private final PasswordField passwordField;
    @Resource
    private AuthenticationService authenticationService;

    public SignInForm() {
        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        Button signInButton = new Button("Sign In", clickEvent -> signIn());
        signInButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        usernameField.setWidth(300, Unit.PIXELS);
        passwordField.setWidth(300, Unit.PIXELS);
        signInButton.setWidth(100, Unit.PIXELS);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(usernameField, passwordField, signInButton);
    }

    public void refresh() {
        usernameField.clear();
        passwordField.clear();
        usernameField.focus();
    }

    private void signIn() {
        try {
            signIn(usernameField.getValue(), passwordField.getValue());
        } catch (HwscException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
        }
    }

    private void signIn(String username, String password) {
        authenticationService.signIn(username, password);
        UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
    }
}
