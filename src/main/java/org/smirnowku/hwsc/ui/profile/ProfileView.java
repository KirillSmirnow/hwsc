package org.smirnowku.hwsc.ui.profile;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.ui.Views;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringView(name = Views.PROFILE)
public class ProfileView extends VerticalLayout implements View {

    @Resource
    private ProfileHeader profileHeader;

    @Resource
    private HomeworksTab homeworksTab;

    @Resource
    private ClassroomsTab classroomsTab;

    @Resource
    private ChecksTab checksTab;

    @Resource
    private HwTemplatesTab hwTemplatesTab;

    private final TabSheet tabSheet;

    public ProfileView() {
        tabSheet = new TabSheet();
    }

    @PostConstruct
    public void init() {
        tabSheet.addTab(classroomsTab).setCaption("Classrooms");
        tabSheet.addTab(homeworksTab).setCaption("Homework");
        tabSheet.addTab(checksTab).setCaption("Checks");
        tabSheet.addTab(hwTemplatesTab).setCaption("HW Templates");
        tabSheet.setSelectedTab(homeworksTab);
        addComponents(profileHeader, tabSheet);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        profileHeader.refresh();
        homeworksTab.refresh();
        classroomsTab.refresh();
        hwTemplatesTab.refresh();
        checksTab.refresh();
    }
}
