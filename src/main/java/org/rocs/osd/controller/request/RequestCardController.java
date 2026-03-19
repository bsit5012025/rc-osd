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

public class RequestCardController {

    @FXML
    private VBox cardRoot;
    @FXML
    private Label deptLabel, nameLabel, typeLabel, reasonLabel;
    @FXML
    private VBox expandedSection;
    @FXML
    private HBox actionBar;
    @FXML
    private ImageView arrowIcon;
    @FXML
    private VBox popupBox;
    @FXML
    private Label popupLabel;

    private RequestFacade requestFacade;
    private long requestId;

    private boolean isExpanded = false;

    private Runnable onActionComplete;

    @FXML
    public void initialize() {
        RequestDao requestDao = new RequestDaoImpl();
        requestFacade = new RequestFacadeImpl(requestDao);
    }

    public void setData(String dept, String name, String type, String reason, long requestId) {
        if (deptLabel != null) deptLabel.setText(dept);
        if (nameLabel != null) nameLabel.setText(name);
        if (typeLabel != null) typeLabel.setText(type);
        if (reasonLabel != null) reasonLabel.setText(reason);

        this.requestId = requestId;
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