package org.smirnowku.hwsc.ui.profile;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class ProfileHeader extends HorizontalLayout {

    @Resource
    private AuthenticationService authenticationService;

    private Label userLabel;
    private Button signOutButton;

    public ProfileHeader() {
        userLabel = new Label("User", ContentMode.HTML);
        signOutButton = new Button("Sign Out", clickEvent -> signOut());
        addComponents(userLabel, signOutButton);

        setSizeFull();
        setComponentAlignment(userLabel, Alignment.MIDDLE_LEFT);
        setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);
    }

    public void refresh() {
        userLabel.setValue(String.format("User: <b>%s</b>", authenticationService.getUser().getName()));
    }

    private void signOut() {
        authenticationService.signOut();
        UI.getCurrent().getNavigator().navigateTo(Views.SIGN_IN);
    }
}
