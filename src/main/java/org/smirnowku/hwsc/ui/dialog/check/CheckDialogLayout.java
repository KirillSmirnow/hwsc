package org.smirnowku.hwsc.ui.dialog.check;

import com.vaadin.ui.*;
import org.smirnowku.hwsc.ui.dialog.CloseDialogListener;

import java.util.Arrays;
import java.util.Optional;

public class CheckDialogLayout extends VerticalLayout {

    private final CheckListener checkListener;
    private final CloseDialogListener closeDialogListener;

    private final ComboBox<Integer> scoresComboBox;

    public CheckDialogLayout(CheckListener checkListener, CloseDialogListener closeDialogListener) {
        this.checkListener = checkListener;
        this.closeDialogListener = closeDialogListener;
        scoresComboBox = new ComboBox<>("Score", Arrays.asList(0, 1));
        scoresComboBox.setEmptySelectionAllowed(false);
        scoresComboBox.setWidth(300, Unit.PIXELS);
        Button confirmButton = new Button("Submit", this::onSubmitButtonClick);
        Button cancelButton = new Button("Cancel", clickEvent -> closeDialogListener.onClose());
        HorizontalLayout buttonsLayout = new HorizontalLayout(confirmButton, cancelButton);
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(scoresComboBox, buttonsLayout);
    }

    private void onSubmitButtonClick(Button.ClickEvent clickEvent) {
        Optional<Integer> score = scoresComboBox.getSelectedItem();
        if (score.isPresent()) {
            if (checkListener.onCheck(score.get())) closeDialogListener.onClose();
        } else {
            Notification.show("Select score");
        }
    }
}
