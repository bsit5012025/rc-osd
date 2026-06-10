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

/**
 * Controller for handling request card UI behavior.
 * Manages rendering request context data
 * and verification validation actions.
 */
public class RequestCardController {

    /** Root container of the request card. */
    @FXML
    private VBox cardRoot;

    /** Label displaying the department name. */
    @FXML
    private Label deptLabel;

    /** Label displaying the requester's full name. */
    @FXML
    private Label nameLabel;

    /** Label displaying the specific type of request. */
    @FXML
    private Label typeLabel;

    /** Label displaying the stated
     * reason for the request. */
    @FXML
    private Label reasonLabel;

    /** Container section that holds fields
     * revealed upon expansion. */
    @FXML
    private VBox expandedSection;

    /** Action bar container holding the
     * approved and deny buttons. */
    @FXML
    private HBox actionBar;

    /** Graphical arrow icon used to
     * toggle the card's expanded state. */
    @FXML
    private ImageView arrowIcon;

    /** Container box used to overlay or
     * display transient action popups. */
    @FXML
    private VBox popupBox;

    /** Label text displayed inside the
     * action status popup box. */
    @FXML
    private Label popupLabel;

    /** Input text area designated for
     * typing processing comments or remarks. */
    @FXML
    private TextArea commentArea;

    /** Layout container hosting the inline error strip view. */
    @FXML
    private HBox errorBannerContainer;

    /** Text element used to present inline
     * validation error messages directly. */
    @FXML
    private Label inlineErrorText;

    /** The callback executed when an action finishes. */
    private Runnable onActionComplete;

    /** Tracks whether the card is expanded or collapsed. */
    private boolean isExpanded = false;

    /** Facade instance for managing request database actions. */
    private RequestFacade requestFacade;

    /** Unique identification number for this request. */
    private long cardId;

    /** Timer used to automatically dismiss the error banner. */
    private PauseTransition errorHideDelay;

    /**
     * Sets the callback executed when an action finishes.
     *
     * @param callback the action completion callback
     */
    public void setOnActionComplete(Runnable callback) {
        this.onActionComplete = callback;
    }

    /**
     * Initializes the request card controller
     * and its default layout visibility states.
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
     * Sets up the contextual student information
     * text to display in the card layout.
     *
     * @param pDept     the department code text
     * @param pName     the name of the student requester
     * @param pType     the type of request being handled
     * @param pReason   the text explaining the reason for the request
     * @param requestId the unique database identity row tracking key
     */
    public void setData(String pDept, String pName,
                        String pType, String pReason,
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
     * Triggered when the user clicks the Approve button.
     * Prompts for confirmation and updates
     * the database tracking status.
     */
    @FXML
    public void onApprove() {
        showConfirmation("Are you sure you want to",
                "approve this request?", "Approve",
                "Cancel", () -> {
            String remarks = null;
            if (commentArea != null && !commentArea.getText(
            ).trim().isEmpty()) {
                remarks = commentArea.getText();
            }
            requestFacade.updateRequestStatus(cardId, remarks,
                    RequestStatus.APPROVED);
            showPopupAndRemoveCard("Request approved!");
        });
    }

    /**
     * Triggered when the user clicks the Deny button.
     * Validates that remarks are provided
     * before allowing submission.
     */
    @FXML
    public void onDeny() {
        if (commentArea == null || commentArea.getText(

        ).trim().isEmpty()) {
            showError("Please enter remarks before denying.");
            return;
        }

        showConfirmation("Are you sure you want to",
                "deny this request?", "Deny",
                "Cancel", () -> {
            requestFacade.updateRequestStatus(cardId,
                    commentArea.getText(), RequestStatus.DENIED);
            showPopupAndRemoveCard("Request denied!");
        });
    }

    /**
     * Displays a temporary warning bar
     * directly inside the card view container.
     *
     * @param message the warning alert string text to show
     */
    private void showError(String message) {
        if (errorBannerContainer == null || inlineErrorText == null) {
            return;
        }

        if (errorHideDelay != null) {
            errorHideDelay.stop();
        }

        inlineErrorText.setText(message);
        errorBannerContainer.setVisible(true);
        errorBannerContainer.setManaged(true);

        errorHideDelay = new PauseTransition(Duration.seconds(3));
        errorHideDelay.setOnFinished(e -> {
            errorBannerContainer.setVisible(false);
            errorBannerContainer.setManaged(false);
        });
        errorHideDelay.play();
    }

    /**
     * Opens a modal popup to confirm important administrative actions.
     *
     * @param l1         first row string label description
     * @param l2         second row string label description
     * @param confirmTxt label configuration for confirmation button
     * @param cancelTxt  label configuration for closing button
     * @param action     the code logic sequence execution
     *                   route mapping callback
     */
    private void showConfirmation(String l1, String l2,
                                  String confirmTxt, String cancelTxt,
                                  Runnable action) {
        try {
            String path = "/view/dialogs/confirmation.fxml";
            URL resource = getClass().getResource(path);
            if (resource == null) {
                path = "/org/rocs/osd/view/dialogs/confirmation.fxml";
                resource = getClass().getResource(path);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            StackPane popupRoot = loader.load();

            ConfirmationDialogController controller = loader.getController();
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders a success box popover feedback
     * tracking state, then unlinks card elements.
     *
     * @param message completion text notice description to display
     */
    private void showPopupAndRemoveCard(String message) {
        if (popupLabel != null) {
            popupLabel.setText(message);
        }
        if (popupBox != null) {
            popupBox.setVisible(true);
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> {
            if (onActionComplete != null) {
                onActionComplete.run();
            }
            if (cardRoot != null && cardRoot.getParent() instanceof Pane) {
                Pane parent = (Pane) cardRoot.getParent();
                parent.getChildren().remove(cardRoot);
            }
        });
        delay.play();
    }

    /**
     * Expands or collapses the extra content
     * fields inside the layout view card window.
     */
    @FXML
    public void toggleExpansion() {
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
     * Swaps navigation button directional images
     * to coordinate with expanded states.
     */
    private void updateIcon() {
        if (arrowIcon == null) {
            return;
        }

        String imgPath = "/assets/rightButton.png";
        if (isExpanded) {
            imgPath = "/assets/downButton.png";
        }

        try {
            URL url = getClass().getResource(imgPath);
            if (url != null) {
                arrowIcon.setImage(new Image(url.toExternalForm()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
