package org.rocs.osd.controller.appeal;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Controller for handling denied appeal card behavior.
 */
public class DeniedAppealCardController {

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
     * Tracks whether the card is expanded or collapsed.
     */
    private boolean isExpanded = false;

    /**
     * Toggles the expansion state of the card.
     */
    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;
        expandedSection.setVisible(isExpanded);
        expandedSection.setManaged(isExpanded);

        String imgPath = isExpanded ? "/assets/downButton.png"
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
