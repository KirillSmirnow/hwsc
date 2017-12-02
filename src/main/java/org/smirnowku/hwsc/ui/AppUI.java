package org.smirnowku.hwsc.ui;

import com.vaadin.annotations.Title;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@SpringUI
@Title("HWSC")
public class AppUI extends UI {

    @Resource
    private SpringNavigator navigator;

    @Resource
    private AuthenticationService authenticationService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        navigator.init(this, this);
        navigator.setErrorView(ErrorView.class);
        navigator.addViewChangeListener(this::beforeViewChange);
    }

    private boolean beforeViewChange(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        switch (viewChangeEvent.getViewName()) {
            case Views.SIGN_IN:
            case Views.SIGN_UP:
                if (authenticationService.isAuthenticated()) {
                    navigator.navigateTo(Views.PROFILE);
                    return false;
                }
                break;

            case Views.PROFILE:
            case Views.CLASSROOM:
            case Views.HW_TEMPLATE:
                if (!authenticationService.isAuthenticated()) {
                    navigator.navigateTo(Views.SIGN_IN);
                    return false;
                }
                break;
        }
        return true;
    }
}
