package org.rocs.osd.controller.request;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.rocs.osd.data.dao.employee.EmployeeDao;
import org.rocs.osd.data.dao.employee.impl.EmployeeDaoImpl;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.data.dao.request.impl.RequestDaoImpl;
import org.rocs.osd.facade.employee.EmployeeFacade;
import org.rocs.osd.facade.employee.impl.EmployeeFacadeImpl;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.facade.request.impl.RequestFacadeImpl;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

/**
 * Controller for handling request card UI behavior.
 * This handles displaying request information.
 */
public class RequestCardController {
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

    @FXML
    private VBox popupBox;
    @FXML
    private Label popupLabel;

    /**
     * Tracks whether the card is expanded or collapsed.
     */
    private boolean isExpanded = false;

    private RequestFacade requestFacade;
    private long requestId;

    @FXML
    public void initialize() {
        RequestDao requestDao = new RequestDaoImpl();
        requestFacade = new RequestFacadeImpl(requestDao);
    }

    /**
     * Sets the data for the request card.
     * @param pDept the department name.
     * @param pName the requester name.
     * @param pType the request type.
     * @param pReason the reason for the request.
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

        this.requestId = requestId;
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

    @FXML
    private void onApprove() {
        requestFacade.updateRequestStatus(requestId, RequestStatus.APPROVED);
        showPopupAndRemoveCard("Request approved!");
    }

    @FXML
    private void onDeny() {
        requestFacade.updateRequestStatus(requestId, RequestStatus.DENIED);
        showPopupAndRemoveCard("Request Denied!");
    }

    private void showPopupAndRemoveCard(String message) {
        popupLabel.setText(message);
        popupBox.setVisible(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            if (cardRoot != null && cardRoot.getParent() instanceof VBox parentVBox) {
                parentVBox.getChildren().remove(cardRoot);
            }
        });

        delay.play();
    }
}