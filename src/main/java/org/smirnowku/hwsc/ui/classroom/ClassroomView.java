package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.exception.BaseException;
import org.smirnowku.hwsc.core.service.ClassroomService;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.ui.Views;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;
import org.smirnowku.hwsc.ui.classroom.actions.EditClassroomListener;
import org.smirnowku.hwsc.ui.classroom.actions.MemberRole;
import org.smirnowku.hwsc.ui.dialog.addmembers.AddMemberListener;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = Views.CLASSROOM)
public class ClassroomView extends VerticalLayout implements View, AddMemberListener, EditClassroomListener {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private ClassroomService classroomService;

    @Resource
    private ClassroomHeader classroomHeader;

    @Resource
    private ClassroomCenterLayout classroomCenterLayout;

    private ClassroomDto classroom;

    @PostConstruct
    public void init() {
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(classroomHeader, classroomCenterLayout);
        classroomHeader.setListeners(this, this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        try {
            refresh(Long.valueOf(viewChangeEvent.getParameters()));
        } catch (NumberFormatException e) {
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
        }
    }

    @Override
    public boolean onEditClassroom(ClassroomDto classroom) {
        try {
            classroomService.edit(authenticationService.getUsername(), classroom.getId(), classroom);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            refresh(classroom.getId());
            return false;
        }
        refresh(classroom.getId());
        return true;
    }

    @Override
    public boolean onAddStudent(String username) {
        return onAddMember(username, MemberRole.STUDENT);
    }

    @Override
    public boolean onAddTeacher(String username) {
        return onAddMember(username, MemberRole.TEACHER);
    }

    private boolean onAddMember(String username, MemberRole role) {
        List<String> studentsUsernames = new ArrayList<>();
        List<String> teachersUsernames = new ArrayList<>();
        if (role == MemberRole.STUDENT) studentsUsernames.add(username);
        if (role == MemberRole.TEACHER) teachersUsernames.add(username);
        try {
            classroomService.addMembers(authenticationService.getUsername(), classroom.getId(), studentsUsernames, teachersUsernames);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            return false;
        } catch (Exception e) {
            Notification.show("Member was not added", Notification.Type.WARNING_MESSAGE);
            return false;
        }
        Notification.show("Done");
        refresh(classroom.getId());
        return true;
    }

    private void refresh(Long id) {
        ClassroomDto classroom;
        try {
            classroom = classroomService.get(authenticationService.getUsername(), id);
        } catch (BaseException e) {
            Notification.show(e.getMessage(), Notification.Type.WARNING_MESSAGE);
            UI.getCurrent().getNavigator().navigateTo(Views.PROFILE);
            return;
        }
        this.classroom = classroom;
        refresh();
    }

    private void refresh() {
        classroomHeader.refresh(classroom);
        classroomCenterLayout.refresh(classroom);
    }
}
