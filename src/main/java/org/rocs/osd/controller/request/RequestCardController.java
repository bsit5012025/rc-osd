package org.rocs.osd.controller.request;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RequestCardController {

    @FXML private Label deptLabel, nameLabel, typeLabel, reasonLabel;
    @FXML private VBox expandedSection;
    @FXML private HBox actionBar;
    @FXML private ImageView arrowIcon;

    private boolean isExpanded = false;

    public void setData(String dept, String name, String type, String reason) {
        if (deptLabel != null) deptLabel.setText(dept);
        if (nameLabel != null) nameLabel.setText(name);
        if (typeLabel != null) typeLabel.setText(type);
        if (reasonLabel != null) reasonLabel.setText(reason);
    }

    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;
        expandedSection.setVisible(isExpanded);
        actionBar.setVisible(isExpanded);
        expandedSection.setManaged(isExpanded);
        actionBar.setManaged(isExpanded);
        updateIcon();
    }

    private void updateIcon() {
        if (arrowIcon == null) return;
        String imgPath = isExpanded ? "/assets/downButton.png" : "/assets/rightButton.png";
        try {
            Image newImg = new Image(getClass().getResourceAsStream(imgPath));
            arrowIcon.setImage(newImg);
        } catch (Exception e) {
            System.out.println("Wait, missing icon: " + imgPath);
        }
    }
}