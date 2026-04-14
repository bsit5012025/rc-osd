package org.rocs.osd.controller.appeal;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controller for appeal confirmation popup.
 * Handles confirm and close actions.
 */
public class AppealConfirmationController {

    /**
     * Callback executed when confirm is clicked.
     */
    private Runnable onConfirm;

    /**
     * Callback executed when cancel is clicked.
     */
    private Runnable onCancel;

    /**
     * Sets confirm action callback.
     * @param pOnConfirm action to execute on confirm
     */
    public void setOnConfirm(Runnable pOnConfirm) {
        this.onConfirm = pOnConfirm;
    }

    /**
     * Sets cancel action callback.
     * @param pOnCancel action to execute on confirm
     */
    public void setOnCancel(Runnable pOnCancel) {
        this.onCancel = pOnCancel;
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
