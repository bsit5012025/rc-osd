package org.rocs.osd.controller.appeal;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;

public class AppealModalController {

    @FXML private Label studentIdLabel;
    @FXML private Label studentNameLabel;
    @FXML private Label offenseLabel;
    @FXML private Label reasonLabel;
    @FXML private VBox expandedSection;
    @FXML private Button arrowButton;
    @FXML private Button approveButton;
    @FXML private Button denyButton;
    @FXML private VBox popupBox;
    @FXML private Label popupLabel;
    @FXML private ImageView arrowIcon;

    private AppealFacade appealFacade = new AppealFacadeImpl();
    private Appeal appeal;
    private Student student;
    private Offense offense;

    private Runnable onActionComplete;

    public void setAppeal(Object[] row) {
        this.appeal = (Appeal) row[0];
        this.student = (Student) row[1];
        this.offense = (Offense) row[2];
        loadAppealData();
    }

    public void setOnActionComplete(Runnable callback) {
        this.onActionComplete = callback;
    }

    @FXML
    public void initialize() {
        arrowButton.setOnAction(event -> toggleExpandedSection());
        approveButton.setOnAction(event -> handleAppealApprove());
        denyButton.setOnAction(event -> handleAppealDeny());

        if (expandedSection != null) expandedSection.setVisible(false);
        if (popupBox != null) popupBox.setVisible(false);
    }

    private void loadAppealData() {
        if (appeal == null || student == null || offense == null) return;

        studentIdLabel.setText(student.getStudentId());
        studentNameLabel.setText(student.getFirstName() + " " + student.getLastName());
        offenseLabel.setText(offense.getOffense());
        reasonLabel.setText(appeal.getMessage());

        expandedSection.setVisible(false);
        popupBox.setVisible(false);
    }

    private void toggleExpandedSection() {
        boolean expanded = expandedSection.isVisible();
        expandedSection.setVisible(!expanded);
        arrowIcon.setRotate(expanded ? 0 : 180);
    }

    private void handleAppealApprove() {
        appealFacade.approveAppeal(appeal.getAppealID());
        showPopupAndRemoveCard("Appeal approved!");
    }

    private void handleAppealDeny() {
        appealFacade.deniedAppeal(appeal.getAppealID());
        showPopupAndRemoveCard("Appeal denied!");
    }

    private void showPopupAndRemoveCard(String message) {
        popupLabel.setText(message);
        popupBox.setVisible(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> {
            if (onActionComplete != null) onActionComplete.run();
        });

        delay.play();
    }
}