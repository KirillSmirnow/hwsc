package org.smirnowku.hwsc.ui.dialog.prompt;

import com.vaadin.ui.*;
import org.smirnowku.hwsc.ui.dialog.CloseDialogListener;

public class PromptDialogLayout extends VerticalLayout {

    private final PromptListener promptListener;
    private final CloseDialogListener closeDialogListener;

    private final TextField valueField;

    public PromptDialogLayout(String valueCaption, String value, String confirmButtonCaption, String cancelButtonCaption,
                              PromptListener promptListener, CloseDialogListener closeDialogListener) {
        this.promptListener = promptListener;
        this.closeDialogListener = closeDialogListener;
        valueField = new TextField(valueCaption, value == null ? "" : value);
        valueField.setWidth(300, Unit.PIXELS);
        Button confirmButton = new Button(confirmButtonCaption, this::onConfirmButtonClick);
        Button cancelButton = new Button(cancelButtonCaption, clickEvent -> closeDialogListener.onClose());
        HorizontalLayout buttonsLayout = new HorizontalLayout(confirmButton, cancelButton);
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(valueField, buttonsLayout);
    }

    private void onConfirmButtonClick(Button.ClickEvent clickEvent) {
        if (promptListener.onPrompt(valueField.getValue()))
            closeDialogListener.onClose();
    }
}
