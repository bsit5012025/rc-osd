package org.rocs.osd.controller.request;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RequestConfirmationController {
    /**
     * Callback executed when confirm is clicked.
     */
    private Runnable onConfirm;

    /**
     * Sets confirm action callback.
     * @param pOnConfirm action to execute on confirm
     */
    public void setOnConfirm(Runnable pOnConfirm) {
        this.onConfirm = pOnConfirm;
    }

    /**
     * Root container of the popup.
     */
    @FXML
    private StackPane root;

    /**
     * Closes the popup window.
     */
    @FXML
    private void closePopup() {
        if (root == null || root.getScene() == null) {
            return;
        }
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    /**
     * Handles approve confirmation.
     */
    @FXML
    void handleConfirmApprove() {
        if (onConfirm != null) {
            onConfirm.run();
        }
        closePopup();
    }

    /**
     * Handles deny confirmation.
     */
    @FXML
    void handleConfirmDeny() {
        if (onConfirm != null) {
            onConfirm.run();
        }
        closePopup();
    }
}
