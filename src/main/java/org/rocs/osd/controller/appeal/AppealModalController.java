/**
 * This package contains controllers for managing appeals in
 * the Office of Student Discipline system.
 *
 * It includes classes that handle the retrieval, display,
 * and interaction of appeal records in the user interface.
 */
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
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

/**
 * Controller for a single appeal modal in the Office of Student
 * Discipline System.
 * This class displays appeal details
 * and handles user actions such as approve or deny.
 */
public class AppealModalController {

    /**
     * Label displaying the student ID.
     * Used in the appeal modal.
     */
    @FXML private Label studentIdLabel;
    /**
     * Label displaying the student name.
     * Shows first and last names.
     */
    @FXML private Label studentNameLabel;
    /**
     * Label displaying the offense.
     * Populated from the record remarks.
     */
    @FXML private Label offenseLabel;
    /**
     * Label displaying the appeal reason.
     * Shows message from the appeal.
     */
    @FXML private Label reasonLabel;
    /**
     * Section that expands to show more details.
     * Can be toggled with arrowButton.
     */
    @FXML private VBox expandedSection;
    /**
     * Button used to toggle the expanded section.
     * Changes the arrowIcon rotation when clicked.
     */
    @FXML private Button arrowButton;
    /**
     * Button to approve the appeal.
     * Calls the facade to approve it.
     */
    @FXML private Button approveButton;
    /**
     * Button to deny the appeal.
     * Calls the facade to deny it.
     */
    @FXML private Button denyButton;
    /**
     * Popup container for feedback messages.
     * Visible when an action is done.
     */
    @FXML private VBox popupBox;
    /**
     * Label inside the popup for messages.
     * Displays approval or denial message.
     */
    @FXML private Label popupLabel;
    /**
     * Icon used for expand/collapse arrow.
     * Rotates depending on section visibility.
     */
    @FXML private ImageView arrowIcon;

    /**
     * Facade for handling appeal actions.
     * Uses AppealFacadeImpl as the implementation.
     */
    private AppealFacade appealFacade = new AppealFacadeImpl();
    /**
     * Current appeal being displayed.
     * Set by setAppeal method.
     */
    private Appeal appeal;

    /**
     * Callback executed after an action completes.
     * Can be set via setOnActionComplete.
     */
    private Runnable onActionComplete;


    /**
     * Sets the appeal data to display in the modal.
     *
     * @param appeal the Appeal object containing details to display.
     */
    public void setAppeal(Appeal appeal) {
        this.appeal = appeal;
        loadAppealData();
    }

    /**
     * Sets a callback to be executed after an action
     * (approve/deny) is completed.
     *
     * @param callback a Runnable to run when the
     * action is complete.
     */
    public void setOnActionComplete(Runnable callback) {
        this.onActionComplete = callback;
    }

    /**
     * Initializes the modal controller.
     * Sets button actions and hides expandable and popup sections by default.
     */
    @FXML
    public void initialize() {
        arrowButton.setOnAction(event -> toggleExpandedSection());
        approveButton.setOnAction(event -> handleAppealApprove());
        denyButton.setOnAction(event -> handleAppealDeny());

        if (expandedSection != null) {
            expandedSection.setVisible(false);
            expandedSection.setManaged(false);
        }
        if (popupBox != null) popupBox.setVisible(false);
    }

    /**
     * Loads appeal details into the modal labels.
     * Hides sections initially.
     */
    private void loadAppealData() {
        if (appeal == null) return;

        Enrollment enrollment = appeal.getEnrollment();
        Record record = appeal.getRecord();
        Student student = enrollment.getStudent();

        studentIdLabel.setText(enrollment.getStudent().getStudentId());
        studentNameLabel.setText(student.getFirstName()
        +" " + student.getLastName());
        offenseLabel.setText(record.getRemarks());
        reasonLabel.setText(appeal.getMessage());

        expandedSection.setVisible(false);
        expandedSection.setManaged(false);
        popupBox.setVisible(false);
    }

    /**
     * Toggles the expanded section visibility.
     * Rotates arrow icon accordingly.
     */
    private void toggleExpandedSection() {
        boolean expanded = expandedSection.isVisible();
        expandedSection.setVisible(!expanded);
        expandedSection.setManaged(!expanded);

        arrowIcon.setRotate(expanded ? 0 : 90);
    }

    /**
     * Approves the appeal via facade.
     * Shows popup after approval.
     */
    private void handleAppealApprove() {
        appealFacade.approveAppeal(appeal.getAppealID());
        showPopupAndRemoveCard("Appeal approved!");
    }

    /**
     * Denies the appeal via facade.
     * Shows popup after denial.
     */
    private void handleAppealDeny() {
        appealFacade.deniedAppeal(appeal.getAppealID());
        showPopupAndRemoveCard("Appeal denied!");
    }

    /**
     * Shows a temporary popup message.
     * Executes onActionComplete after delay.
     *
     * @param message the message to show in the popup.
     */
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
