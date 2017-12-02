package org.smirnowku.hwsc.ui.profile;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.smirnowku.hwsc.core.service.impl.CheckService;
import org.smirnowku.hwsc.dto.CheckDto;
import org.smirnowku.hwsc.ui.auth.AuthenticationService;

import javax.annotation.Resource;

@UIScope
@SpringComponent
public class ChecksTab extends VerticalLayout {

    @Resource
    private AuthenticationService authenticationService;

    @Resource
    private CheckService checkService;

    private Grid<CheckDto> pendingGrid;
    private Grid<CheckDto> checkedGrid;

    public ChecksTab() {
        pendingGrid = new Grid<>("Pending");
        pendingGrid.addColumn(check -> check.getAssignment().getHomework().getName()).setCaption("Homework");

        checkedGrid = new Grid<>("Checked");
        checkedGrid.addColumn(check -> check.getAssignment().getHomework().getName()).setCaption("Homework");
        checkedGrid.addColumn(check -> check.getAssignment().getScore()).setCaption("Score");

        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        pendingGrid.setSizeFull();
        checkedGrid.setSizeFull();

        addComponents(pendingGrid, checkedGrid);
    }

    public void refresh() {
        pendingGrid.setItems(checkService.getPending(authenticationService.getUsername()));
        checkedGrid.setItems(checkService.getChecked(authenticationService.getUsername()));
    }
}
