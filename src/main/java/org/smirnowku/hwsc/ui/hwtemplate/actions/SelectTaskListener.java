package org.smirnowku.hwsc.ui.hwtemplate.actions;

import com.vaadin.event.selection.SelectionEvent;
import org.smirnowku.hwsc.dto.TaskTemplateDto;

public interface SelectTaskListener {

    void onSelect(SelectionEvent<TaskTemplateDto> selectionEvent);
}
