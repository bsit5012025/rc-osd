package org.rocs.osd.controller.appeal;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
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
 * Controller for a single appeal card in the
 * Office of Student Discipline System.
 * This class displays appeal details and handles user actions
 * such as approve or deny.
 */
public class AppealCardController {

    /** Student ID label. */
    @FXML private Label studentIdLabel;

    /** Student name label. */
    @FXML private Label studentNameLabel;

    /** Offense label. */
    @FXML private Label offenseLabel;

    /** Reason label. */
    @FXML private Label reasonLabel;

    /** Popup label. */
    @FXML private Label popupLabel;

    /** Expanded section container. */
    @FXML private VBox expandedSection;

    /** Popup container. */
    @FXML private VBox popupBox;

    /** Action buttons container. */
    @FXML private HBox actionBar;

    /** Arrow icon image. */
    @FXML private ImageView arrowIcon;

    /** Expansion state flag. */
    private boolean isExpanded = false;

    /** Facade for appeal operations. */
    private AppealFacade appealFacade = new AppealFacadeImpl();

    /** Current appeal data. */
    private Appeal appeal;

    /** Callback after action. */
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
        if (popupBox != null) {
            popupBox.setVisible(false);
        }
    }

    /**
     * Sets the appeal data to display in the card.
     * @param appealData the Appeal object containing details to display.
     */
    public void setAppeal(Appeal appealData) {
        this.appeal = appealData;
    }

    /**
     * Sets a callback to be executed after an action
     * (approve/deny) is completed.
     * @param callback a Runnable to run when the action is complete.
     */
    public void setOnActionComplete(Runnable callback) {
        this.onActionComplete = callback;
    }

    /**
     * Sets mock data for the card.
     * @param id student ID
     * @param name student name
     * @param offense offense description
     * @param reasonText reason for appeal
     */
    public void setData(String id, String name,
                        String offense, String reasonText) {

        if (studentIdLabel != null) {
            studentIdLabel.setText(id);
        }
        if (studentNameLabel != null) {
            studentNameLabel.setText(name);
        }
        if (offenseLabel != null) {
            offenseLabel.setText(offense);
        }
        if (reasonLabel != null) {
            reasonLabel.setText(reasonText);
        }
    }

    private void loadAppealData() {
        if (appeal == null) {
            return;
        }
        Enrollment enrollment = appeal.getEnrollment();
        Record record = appeal.getRecord();
        Student student = enrollment.getStudent();
        if (studentIdLabel != null) {
            studentIdLabel.setText(student.getStudentId());
        }
        if (studentNameLabel != null) {
            studentNameLabel.setText(
                    student.getFirstName() + " " + student.getLastName()
            );
        }
        if (offenseLabel != null) {
            offenseLabel.setText(record.getRemarks());
        }
        if (reasonLabel != null) {
            reasonLabel.setText(appeal.getMessage());
        }
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
        if (arrowIcon == null) {
            return;
        }
        String imgPath = isExpanded ? "/assets/downButton.png"
                : "/assets/rightButton.png";
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
            appealFacade.denyAppeal(appeal.getAppealID());
        }
        showPopupAndRemoveCard("Appeal denied!");
    }

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
        });
        delay.play();
    }
}
