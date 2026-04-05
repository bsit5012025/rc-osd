package org.rocs.osd.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Reusable confirmation dialog controller.
 */
public class ConfirmationDialogController {

    /**
     * Label for the first line of the dialog message.
     */
    @FXML
    private Label messageLine1;

    /**
     * Label for the second line of the dialog message.
     */
    @FXML
    private Label messageLine2;

    /**
     * Button to trigger the confirmation action and close the dialog.
     */
    @FXML
    private Button confirmButton;

    /**
     * Button to dismiss the dialog without taking action.
     */
    @FXML
    private Button cancelButton;

    /**
     * The task to be executed when the user clicks the confirm button.
     */
    private Runnable onConfirmAction;

    /**
     * Configures the text displayed in the dialog's message labels.
     *
     * @param line1 The primary message text.
     * @param line2 The secondary message text
     * (useful for descriptions or warnings).
     */
    public void setMessage(String line1, String line2) {
        if (messageLine1 != null) {
            messageLine1.setText(line1);
        }

        if (messageLine2 != null) {
            messageLine2.setText(line2);
        }
    }

    /**
     * Dynamically updates the labels for the confirm and cancel buttons.
     *
     * @param confirmText The text to display on the confirmation button.
     * @param cancelText  The text to display on the cancellation button.
     */
    public void setButtonLabels(String confirmText, String cancelText) {
        confirmButton.setText(confirmText);
        cancelButton.setText(cancelText);
    }

    /**
     * Assigns a {@link Runnable} action to be executed upon confirmation.
     *
     * @param action The logic to run when the user clicks confirm.
     */
    public void setOnConfirm(Runnable action) {
        this.onConfirmAction = action;
    }

    @FXML
    private void handleConfirm() {
        if (onConfirmAction != null) {
            onConfirmAction.run();
        }
        close();
    }

    @FXML
    private void handleCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}
