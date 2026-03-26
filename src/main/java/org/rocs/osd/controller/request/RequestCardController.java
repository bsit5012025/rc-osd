package org.rocs.osd.controller.request;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.data.dao.request.impl.RequestDaoImpl;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.facade.request.impl.RequestFacadeImpl;
import org.rocs.osd.model.request.RequestStatus;

/**
 * Controller for handling request card UI behavior.
 * This handles displaying request information.
 */
public class RequestCardController {

    /**
     * ID for the whole fxml card.
     */
    @FXML
    private VBox cardRoot;
    /**
     * Labels for displaying request details:
     * department, name, type, and reason.
     */
    @FXML
    private Label deptLabel;
    /**
     * Label displaying the name of the requester.
     */
    @FXML
    private Label nameLabel;
    /**
     * Label displaying the type of the request.
     */
    @FXML
    private Label typeLabel;
    /**
     * Label displaying the reason for the request.
     */
    @FXML
    private Label reasonLabel;
    /**
     * Section that becomes visible when the card is expanded.
     */
    @FXML
    private VBox expandedSection;
    /**
     * Container for action buttons (e.g., approve/deny).
     */
    @FXML
    private HBox actionBar;
    /**
     * Arrow icon used to indicate expand/collapse state.
     */
    @FXML
    private ImageView arrowIcon;
    /**
     * Holder of the popup label.
     */
    @FXML
    private VBox popupBox;
    /**
     * For the state of the request if the
     * request is successfully approved or deny.
     */
    @FXML
    private Label popupLabel;
    /**
     * Holder for user comments where it will be
     * stored on the remarks when approving or denying.
     */
    @FXML
    private TextArea commentArea;

    /**
     * Tracks whether the card is expanded or collapsed.
     */
    private boolean isExpanded = false;

    /** Facade for handling request operations. */
    private RequestFacade requestFacade;

    /** ID of the current request. */
    private long cardId;

    /** Initializes the dashboard controller and loads request data. */
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

        RequestDao requestDao = new RequestDaoImpl();
        requestFacade = new RequestFacadeImpl(requestDao);
    }

    /**
     * Sets the data for the request card.
     * @param pDept the department name.
     * @param pName the requester name.
     * @param pType the request type.
     * @param pReason the reason for the request.
     * @param requestId the number for the specific cards
     */
    public void setData(String pDept, String pName,
    String pType, String pReason, long requestId) {
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
     * Toggles the expansion state of the card.
     */
    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;
        expandedSection.setVisible(isExpanded);
        actionBar.setVisible(isExpanded);
        expandedSection.setManaged(isExpanded);
        actionBar.setManaged(isExpanded);
        updateIcon();
    }

    /**
     * Updates the arrow icon based on expansion state.
     * Prints an error message if the image is missing.
     */
    private void updateIcon() {
        if (arrowIcon == null) {
            return;
        }
        String imgPath = isExpanded
                ?
        "/assets/downButton.png" : "/assets/rightButton.png";
        try {
            Image newImg = new Image(getClass().getResourceAsStream(imgPath));
            arrowIcon.setImage(newImg);
        } catch (Exception e) {
            System.out.println("Wait, missing icon: " + imgPath);
        }
    }

    /**
     * Approving the request status and add comments.
     */
    @FXML
    private void onApprove() {
        requestFacade.updateRequestStatus(cardId, commentArea.getText(),
                RequestStatus.APPROVED);
        showPopupAndRemoveCard("Request approved!");
    }

    /**
     * Denied the request status and add comments.
     */
    @FXML
    private void onDeny() {
        requestFacade.updateRequestStatus(cardId, commentArea.getText(),
                RequestStatus.DENIED);
        showPopupAndRemoveCard("Request Denied!");
    }

    /**
     * Shows a popup for the card when it is successfully approved or denied.
     *
     * @param message the message to display in the popup.
     */
    private void showPopupAndRemoveCard(String message) {
        popupLabel.setText(message);
        popupBox.setVisible(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            if (cardRoot != null && cardRoot.getParent()
                    instanceof VBox parentVBox) {
                parentVBox.getChildren().remove(cardRoot);
            }
        });

        delay.play();
    }
}
