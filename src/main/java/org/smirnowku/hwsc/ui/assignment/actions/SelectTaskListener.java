package org.smirnowku.hwsc.ui.assignment.actions;

import com.vaadin.event.selection.SelectionEvent;
import org.smirnowku.hwsc.dto.TaskDto;

public interface SelectTaskListener {

    void onSelect(SelectionEvent<TaskDto> selectionEvent);
}
