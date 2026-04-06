package org.rocs.osd.controller.request;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Controller for handling approved request card behavior.
 */
public class ApprovedRequestCardController {

    /**
     * The expandable section of the card.
     */
    @FXML
    private VBox expandedSection;

    /**
     * The arrow icon used to indicate expansion state.
     */
    @FXML
    private ImageView arrowIcon;
    /**
     * Label displaying the name of the requester.
     */
    @FXML
    private Label deptLabel;
    /**
     * Label displaying the name of the requester.
     */
    @FXML
    private Label studentNameLabel;
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
     * Label for displaying the reason
     * why it is approve or denied.
     */
    @FXML
    private TextArea commentArea;

    /**
     * Tracks whether the card is expanded or collapsed.
     */
    private boolean isExpanded = false;

    /** ID of the current request. */
    private long cardId;

    /**
     * Getter for cardId.
     * @return cardId.
     * */
    public long getCardId() {
        return cardId;
    }

    /**
     * Sets the data for the request card.
     * @param pDept the department name.
     * @param pName the requester name.
     * @param pType the request type.
     * @param pReason the reason for the request.
     * @param remarks the remarks of the user approving the request.
     * @param requestId the number for the specific cards
     */
    public void setData(String pDept, String pName,
                        String pType, String pReason,
                        String remarks, long requestId) {
        if (deptLabel != null) {
            deptLabel.setText(pDept);
        }
        if (studentNameLabel != null) {
            studentNameLabel.setText(pName);
        }
        if (typeLabel != null) {
            typeLabel.setText(pType);
        }
        if (reasonLabel != null) {
            reasonLabel.setText(pReason);
        }
        if (commentArea != null) {
            commentArea.setText(remarks);
        }
        cardId = requestId;
    }

    /**
     * Toggles the expansion state of the card.
     */
    @FXML
    void toggleExpansion() {
        isExpanded = !isExpanded;
        expandedSection.setVisible(isExpanded);
        expandedSection.setManaged(isExpanded);

        String imgPath = isExpanded
                ? "/assets/downButton.png"
                : "/assets/rightButton.png";

        try {
            arrowIcon.setImage(new Image(
                    getClass().getResourceAsStream(imgPath)
            ));
        } catch (Exception e) {
            arrowIcon.setRotate(isExpanded ? 90 : 0);
        }
    }
}
