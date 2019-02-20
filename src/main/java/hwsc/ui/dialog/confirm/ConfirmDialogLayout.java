package hwsc.ui.dialog.confirm;

import com.vaadin.ui.*;
import hwsc.ui.dialog.CloseDialogListener;

public class ConfirmDialogLayout extends VerticalLayout {

    private final ConfirmListener confirmListener;
    private final CloseDialogListener closeDialogListener;

    public ConfirmDialogLayout(String message, String confirmButtonCaption, String cancelButtonCaption,
                               ConfirmListener confirmListener, CloseDialogListener closeDialogListener) {
        this.confirmListener = confirmListener;
        this.closeDialogListener = closeDialogListener;
        Label messageLabel = new Label(message);
        Button confirmButton = new Button(confirmButtonCaption, this::onConfirmButtonClick);
        Button cancelButton = new Button(cancelButtonCaption, clickEvent -> closeDialogListener.onClose());
        HorizontalLayout buttonsLayout = new HorizontalLayout(confirmButton, cancelButton);
        setSizeFull();
        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponents(messageLabel, buttonsLayout);
    }

    private void onConfirmButtonClick(Button.ClickEvent clickEvent) {
        if (confirmListener.onConfirm())
            closeDialogListener.onClose();
    }
}
