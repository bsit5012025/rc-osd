package org.rocs.osd.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Reusable error dialog controller.
 */
public class ErrorDialogController {

    /**
     * The label used to display the error message content.
     */
    @FXML
    private Label errorText;

    /**
     * Dynamically sets the error message to be displayed in the dialog.
     *
     * @param message The text description of the error.
     */
    public void setMessage(String message) {
        errorText.setText(message);
    }

    /**
     * Closes dialog.
     */
    public void close() {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }
}
