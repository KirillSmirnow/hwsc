package org.smirnowku.hwsc.ui.dialog.assignhw;

import com.vaadin.ui.*;
import org.smirnowku.hwsc.dto.ClassroomDto;
import org.smirnowku.hwsc.dto.HomeworkDto;
import org.smirnowku.hwsc.ui.dialog.CloseDialogListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AssignHomeworkDialogLayout extends VerticalLayout {

    private AssignListener assignListener;
    private CloseDialogListener closeDialogListener;
    private ComboBox<ClassroomDto> classroomComboBox;
    private DateTimeField deadlineField;
    private ComboBox<Integer> subgroupSizeComboBox;

    public AssignHomeworkDialogLayout(List<ClassroomDto> classrooms,
                                      AssignListener assignListener, CloseDialogListener closeDialogListener) {
        this.assignListener = assignListener;
        this.closeDialogListener = closeDialogListener;
        classroomComboBox = new ComboBox<>("Choose classroom");
        classroomComboBox.setEmptySelectionAllowed(false);
        classroomComboBox.setItemCaptionGenerator(ClassroomDto::getName);
        classroomComboBox.setItems(classrooms);
        deadlineField = new DateTimeField("Deadline");
        deadlineField.setValue(LocalDateTime.now().plusDays(1));
        subgroupSizeComboBox = new ComboBox<>("Subgroup size");
        subgroupSizeComboBox.setEmptySelectionAllowed(false);
        classroomComboBox.addValueChangeListener(valueChangeEvent -> updateAvailableSubgroupSizes());
        Button assignButton = new Button("Assign", clickEvent -> assign());

        classroomComboBox.setWidth(300, Unit.PIXELS);
        deadlineField.setWidth(300, Unit.PIXELS);
        subgroupSizeComboBox.setWidth(300, Unit.PIXELS);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(classroomComboBox, deadlineField, subgroupSizeComboBox, assignButton);
        setComponentAlignment(assignButton, Alignment.BOTTOM_CENTER);
    }

    private void assign() {
        Optional<ClassroomDto> classroom = classroomComboBox.getSelectedItem();
        if (classroom.isPresent()) {
            HomeworkDto homework = new HomeworkDto(deadlineField.getValue(), subgroupSizeComboBox.getValue());
            if (assignListener.onAssign(classroom.get().getId(), homework)) closeDialogListener.onClose();
        } else {
            Notification.show("You should choose classroom", Notification.Type.WARNING_MESSAGE);
        }
    }

    private void updateAvailableSubgroupSizes() {
        Integer studentsQty = 0;
        Optional<ClassroomDto> classroom = classroomComboBox.getSelectedItem();
        if (classroom.isPresent()) studentsQty = classroom.get().getStudents().size();
        subgroupSizeComboBox.setCaption(String.format("Subgroup size (total: %d students)", studentsQty));
        subgroupSizeComboBox.clear();
        subgroupSizeComboBox.setItems(IntStream.rangeClosed(2, studentsQty).boxed().collect(Collectors.toList()));
    }
}
