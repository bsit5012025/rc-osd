package org.rocs.osd.controller.appeal;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ApprovedAppealCardController {

    @FXML private VBox expandedSection;
    @FXML private ImageView arrowIcon;
    private boolean isExpanded = false;

    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;
        expandedSection.setVisible(isExpanded);
        expandedSection.setManaged(isExpanded);

        String imgPath = isExpanded ? "/assets/downButton.png" : "/assets/rightButton.png";
        try {
            arrowIcon.setImage(new Image(getClass().getResourceAsStream(imgPath)));
        } catch (Exception e) {
            arrowIcon.setRotate(isExpanded ? 90 : 0);
        }
    }
}