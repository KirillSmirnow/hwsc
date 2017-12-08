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

    private final Label nameLabel;

    public ProfileHeader() {
        nameLabel = new Label("name", ContentMode.HTML);
        Button signOutButton = new Button("Sign Out", clickEvent -> signOut());

        setSizeFull();
        addComponents(nameLabel, signOutButton);
        setComponentAlignment(nameLabel, Alignment.MIDDLE_LEFT);
        setComponentAlignment(signOutButton, Alignment.MIDDLE_RIGHT);
    }

    public void refresh() {
        nameLabel.setValue(String.format("<b style=\"color:green\">%s</b><br/>%s",
                authenticationService.getUser().getName(), authenticationService.getUsername()));
    }

    private void signOut() {
        authenticationService.signOut();
        UI.getCurrent().getNavigator().navigateTo(Views.SIGN_IN);
    }
}
