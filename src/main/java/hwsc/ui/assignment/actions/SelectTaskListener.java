package hwsc.ui.assignment.actions;

import com.vaadin.event.selection.SelectionEvent;
import hwsc.dto.TaskDto;

public interface SelectTaskListener {

    void onSelect(SelectionEvent<TaskDto> selectionEvent);
}
