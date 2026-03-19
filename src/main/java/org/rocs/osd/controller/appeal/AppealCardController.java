package org.rocs.osd.controller.appeal;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

/**
 * Controller for a single appeal card in the Office of Student Discipline System.
 * This class displays appeal details and handles user actions such as approve or deny.
 */
public class AppealCardController {

    @FXML private Label studentIdLabel, studentNameLabel, offenseLabel, reasonLabel, popupLabel;
    @FXML private VBox expandedSection, popupBox;
    @FXML private HBox actionBar;
    @FXML private ImageView arrowIcon;
    private boolean isExpanded = false;
    private AppealFacade appealFacade = new AppealFacadeImpl();
    private Appeal appeal;
    private Runnable onActionComplete;

    /**
     * Initializes the card controller.
     * Sets default visibility for expandable and popup sections.
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
        if (popupBox != null) popupBox.setVisible(false);
    }

    /**
     * Sets the appeal data to display in the card.
     * @param appeal the Appeal object containing details to display.
     */
    public void setAppeal(Appeal appeal) {
        this.appeal = appeal;
        loadAppealData();
    }

    /**
     * Sets a callback to be executed after an action (approve/deny) is completed.
     * @param callback a Runnable to run when the action is complete.
     */
    public void setOnActionComplete(Runnable callback) {
        this.onActionComplete = callback;
    }

    /**
     * Manually updates labels with string data. Use this for testing/mock data.
     */
    public void setData(String id, String name, String offense, String reasonText) {
        if (studentIdLabel != null) studentIdLabel.setText(id);
        if (studentNameLabel != null) studentNameLabel.setText(name);
        if (offenseLabel != null) offenseLabel.setText(offense);
        if (reasonLabel != null) reasonLabel.setText(reasonText);
    }

    private void loadAppealData() {
        if (appeal == null) return;

        Enrollment enrollment = appeal.getEnrollment();
        Record record = appeal.getRecord();
        Student student = enrollment.getStudent();

        if (studentIdLabel != null) studentIdLabel.setText(student.getStudentId());
        if (studentNameLabel != null) studentNameLabel.setText(student.getFirstName() + " " + student.getLastName());
        if (offenseLabel != null) offenseLabel.setText(record.getRemarks());
        if (reasonLabel != null) reasonLabel.setText(appeal.getMessage());
    }

    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;
        expandedSection.setVisible(isExpanded);
        expandedSection.setManaged(isExpanded);
        if (actionBar != null) {
            actionBar.setVisible(isExpanded);
            actionBar.setManaged(isExpanded);
        }
        updateIcon();
    }

    private void updateIcon() {
        if (arrowIcon == null) return;
        String imgPath = isExpanded ? "/assets/downButton.png" : "/assets/rightButton.png";
        try {
            Image newImg = new Image(getClass().getResourceAsStream(imgPath));
            arrowIcon.setImage(newImg);
        } catch (Exception e) {
            arrowIcon.setRotate(isExpanded ? 90 : 0);
        }
    }

    @FXML
    private void handleAppealApprove() {
        if (appeal != null) {
            appealFacade.approveAppeal(appeal.getAppealID());
        }
        showPopupAndRemoveCard("Appeal approved!");
    }

    @FXML
    private void handleAppealDeny() {
        if (appeal != null) {
            appealFacade.deniedAppeal(appeal.getAppealID());
        }
        showPopupAndRemoveCard("Appeal denied!");
    }

    private void showPopupAndRemoveCard(String message) {
        if (popupLabel != null) popupLabel.setText(message);
        if (popupBox != null) popupBox.setVisible(true);

        PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
        delay.setOnFinished(e -> {
            if (onActionComplete != null) onActionComplete.run();
        });
        delay.play();
    }
}
