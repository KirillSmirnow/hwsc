package org.smirnowku.hwsc.ui.classroom;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import org.smirnowku.hwsc.core.model.Classroom;
import org.smirnowku.hwsc.core.service.impl.ClassroomService;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@UIScope
@SpringComponent
public class ClassroomCenterLayout extends HorizontalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private ClassroomService classroomService;

    @Resource
    private StudentsTab studentsTab;

    @Resource
    private TeachersTab teachersTab;

    @Resource
    private HomeworksLayout homeworksLayout;

    private TabSheet membersTabSheet;

    public ClassroomCenterLayout() {
        membersTabSheet = new TabSheet();
    }

    @PostConstruct
    public void init() {
        membersTabSheet.addTab(studentsTab).setCaption("Students");
        membersTabSheet.addTab(teachersTab).setCaption("Teachers");
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(membersTabSheet, homeworksLayout);
    }

    public void refresh(Classroom classroom) {
        membersTabSheet.setSelectedTab(studentsTab);
        studentsTab.refresh(classroom);
        teachersTab.refresh(classroom);
        homeworksLayout.refresh(classroomService.getHomeworks(authenticationService.getUser().getUsername(),
                classroom.getId()));
    }
}
