package hwsc.ui.hwtemplate.actions;

import com.vaadin.event.selection.SelectionEvent;
import hwsc.dto.TaskTemplateDto;

public interface SelectTaskListener {

    void onSelect(SelectionEvent<TaskTemplateDto> selectionEvent);
}
