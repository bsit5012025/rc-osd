package org.rocs.osd.controller.request;

import java.io.IOException;
import java.net.URL;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.rocs.osd.controller.dialog.ConfirmationDialogController;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.data.dao.request.impl.RequestDaoImpl;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.facade.request.impl.RequestFacadeImpl;
import org.rocs.osd.model.request.RequestStatus;

import java.io.IOException;

/**
 * Controller for handling request card UI behavior.
 * This handles displaying request information.
 */
public class RequestCardController {

    /** Root container of the request card. */
    @FXML
    private VBox cardRoot;

    /** Label displaying department. */
    @FXML
    private Label deptLabel;

    /** Label displaying name. */
    @FXML
    private Label nameLabel;

    /** Label displaying request type. */
    @FXML
    private Label typeLabel;

    /** Label displaying reason. */
    @FXML
    private Label reasonLabel;

    /** Expanded section container. */
    @FXML
    private VBox expandedSection;

    /** Action bar container. */
    @FXML
    private HBox actionBar;

    /** Arrow icon for expand/collapse. */
    @FXML
    private ImageView arrowIcon;

    /** Popup container. */
    @FXML
    private VBox popupBox;

    /** Popup message label. */
    @FXML
    private Label popupLabel;

    /** Text area for comments. */
    @FXML
    private TextArea commentArea;

    /** Error message label. */
    @FXML
    private Label errorLabel;

    /** Callback after action completion. */
    private Runnable onActionComplete;

    /** Flag indicating expanded state. */
    private boolean isExpanded = false;

    /** Request facade instance. */
    private RequestFacade requestFacade;

    /** ID of the request card. */
    private long cardId;

    /**
     * Sets the callback after an action is completed.
     *
     * @param callback the callback to execute
     */
    public void setOnActionComplete(Runnable callback) {
        this.onActionComplete = callback;
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {
        if (expandedSection != null) {
            expandedSection.setVisible(false);
            expandedSection.setManaged(false);
        }

        if (actionBar != null) {
            actionBar.setVisible(false);
            actionBar.setManaged(false);
        }
        if (popupBox != null) {
            popupBox.setVisible(false);
        }

        RequestDao requestDao = new RequestDaoImpl();
        requestFacade = new RequestFacadeImpl(requestDao);
    }

    /**
     * Sets the data for the request card.
     *
     * @param pDept department name
     * @param pName requester name
     * @param pType request type
     * @param pReason request reason
     * @param requestId request ID
     */
    public void setData(
            String pDept,
            String pName,
            String pType,
            String pReason,
            long requestId) {

        if (deptLabel != null) {
            deptLabel.setText(pDept);
        }

        if (nameLabel != null) {
            nameLabel.setText(pName);
        }

        if (typeLabel != null) {
            typeLabel.setText(pType);
        }

        if (reasonLabel != null) {
            reasonLabel.setText(pReason);
        }

        cardId = requestId;
    }

    /**
     * Handles approve action.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void onApprove() {
        showConfirmation(
                "Are you sure you want to",
                "approve this request?",
                "Approve",
                "Cancel",
                () -> {
                    String remarks = null;

                    if (commentArea != null
                            && !commentArea.getText().trim().isEmpty()) {
                        remarks = commentArea.getText();
                    }

                    requestFacade.updateRequestStatus(
                            cardId,
                            remarks,
                            RequestStatus.APPROVED
                    );

                    showPopupAndRemoveCard("Request approved!");
                }
        );
    }

    /**
     * Handles deny action.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void onDeny() {
        if (commentArea == null
                || commentArea.getText().trim().isEmpty()) {

            showError("Please enter remarks before denying.");
            return;
        }

        showConfirmation(
                "Are you sure you want to",
                "deny this request?",
                "Deny",
                "Cancel",
                () -> {
                    requestFacade.updateRequestStatus(
                            cardId,
                            commentArea.getText(),
                            RequestStatus.DENIED
                    );

                    showPopupAndRemoveCard("Request denied!");
                }
        );
    }

    /**
     * Displays confirmation dialog.
     *
     * @param l1 first line
     * @param l2 second line
     * @param confirmTxt confirm button text
     * @param cancelTxt cancel button text
     * @param action action to execute
     */
    private void showConfirmation(
            String l1,
            String l2,
            String confirmTxt,
            String cancelTxt,
            Runnable action) {

        try {
            String path = "/org/rocs/osd/view/dialogs/confirmation.fxml";
            URL resource = getClass().getResource(path);

            if (resource == null) {
                path = "/view/dialogs/confirmation.fxml";
                resource = getClass().getResource(path);
            }

            if (resource == null) {
                throw new IllegalStateException(
                        "FXML file not found at " + path
                );
            }

            FXMLLoader loader = new FXMLLoader(resource);
            StackPane popupRoot = loader.load();

            ConfirmationDialogController controller =
                    loader.getController();

            if (controller != null) {
                controller.setMessage(l1, l2);
                controller.setButtonLabels(confirmTxt, cancelTxt);
                controller.setOnConfirm(action);
            }

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(popupRoot));
            stage.showAndWait();

        } catch (IOException | IllegalStateException e) {
            System.err.println("Popup Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Shows success popup and removes card.
     *
     * @param message message to display
     */
    private void showPopupAndRemoveCard(String message) {
        if (popupLabel != null) {
            popupLabel.setText(message);
        }

        if (popupBox != null) {
            popupBox.setVisible(true);
        }

        PauseTransition delay =
                new PauseTransition(Duration.seconds(1.5));

        delay.setOnFinished(e -> {
            if (onActionComplete != null) {
                onActionComplete.run();
            }

            if (cardRoot != null
                    && cardRoot.getParent() instanceof Pane parent) {

                parent.getChildren().remove(cardRoot);
            }
        });

        delay.play();
    }

    /**
     * Displays error message temporarily.
     *
     * @param message error message
     */
    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }

        PauseTransition delay =
                new PauseTransition(Duration.seconds(3));

        delay.setOnFinished(e -> {
            if (errorLabel != null) {
                errorLabel.setVisible(false);
            }
        });

        delay.play();
    }

    /**
     * Toggles expansion of the card.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void toggleExpansion() {
        isExpanded = !isExpanded;

        if (expandedSection != null) {
            expandedSection.setVisible(isExpanded);
            expandedSection.setManaged(isExpanded);
        }

        if (actionBar != null) {
            actionBar.setVisible(isExpanded);
            actionBar.setManaged(isExpanded);
        }

        updateIcon();
    }

    /**
     * Updates arrow icon based on state.
     */
    private void updateIcon() {
        if (arrowIcon == null) {
            return;
        }

        String imgPath = isExpanded
                ? "/assets/downButton.png"
                : "/assets/rightButton.png";

        try {
            URL url = getClass().getResource(imgPath);

            if (url != null) {
                arrowIcon.setImage(
                        new Image(url.toExternalForm())
                );
            } else {
                System.err.println("Image resource not found: " + imgPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
