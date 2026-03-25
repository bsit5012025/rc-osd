package org.rocs.osd.controller.appeal;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.io.IOException;

/**
 * Controller for a single appeal card in the
 * Office of Student Discipline System.
 * This class displays appeal details and handles user actions
 * such as approve or deny.
 */
public class AppealCardController {

    /**
     * Expanded section container.
     */
    @FXML
    private VBox expandedSection;

    /**
     * Action buttons container.
     */
    @FXML
    private HBox actionBar;

    /**
     * Popup container.
     */
    @FXML
    private VBox popupBox;

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
     * Current appeal data.
     */
    private Appeal appeal;

    /**
     * Sets the appeal data to display in the card.
     * @param appealData the Appeal object containing details to display.
     */
    public void setAppeal(Appeal appealData) {
        this.appeal = appealData;
        loadAppealData();
    }

    /**
     * Callback after action.
     */
    private Runnable onActionComplete;

    /**
     * Sets a callback to be executed after an action
     * (approve/deny) is completed.
     * @param callback a Runnable to run when the action is complete.
     */
    public void setOnActionComplete(Runnable callback) {
        this.onActionComplete = callback;
    }

    /**
     * Student ID label.
     */
    @FXML
    private Label studentIdLabel;

    /**
     * Student name label.
     */
    @FXML
    private Label studentNameLabel;

    /**
     * Offense label.
     */
    @FXML
    private Label offenseLabel;

    /**
     * Reason label.
     */
    @FXML
    private Label reasonLabel;

    /**
     * Loads and displays appeal-related data into the UI components.
     */
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
                if ("DENIED".equals(appeal.getStatus())) {
                    reasonLabel.setText(
                            appeal.getMessage() + "\n\nRemarks: "
                                    + appeal.getRemarks()
                    );
                } else {
                    reasonLabel.setText(appeal.getMessage());
                }
            }
        }


    /**
     * Expansion state flag.
     */
    private boolean isExpanded = false;

    /**
     * Toggles expansion of the card.
     */
    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;

        expandedSection.setVisible(isExpanded);
        expandedSection.setManaged(isExpanded);

        if ("PENDING".equals(appeal.getStatus())) {
            actionBar.setVisible(isExpanded);
            actionBar.setManaged(isExpanded);
        }

        updateIcon();
    }

    /**
     * Arrow icon image.
     */
    @FXML
    private ImageView arrowIcon;

    /**
     * Updates the arrow icon based on expansion state.
     */
    private void updateIcon() {
        if (arrowIcon == null) {
            return;
            }

        String imgPath = isExpanded
                ? "/assets/downButton.png"
                : "/assets/rightButton.png";

        try {
            Image newImg = new Image(getClass().getResourceAsStream(imgPath));
            arrowIcon.setImage(newImg);
        } catch (Exception e) {
            arrowIcon.setRotate(isExpanded ? 90 : 0);
        }
    }

    /**
     * Optional remarks input.
     */
    @FXML
    private TextArea commentArea;

    /**
     * Facade for appeal operations.
     */
    private AppealFacade appealFacade = new AppealFacadeImpl();

    /**
     * Handles appeal approval.
     */
    @FXML
    private void handleAppealApprove() {
    showConfirmation("/view/dialogs/approvedAppealConfirmation.fxml",() -> {

            String remarks = (commentArea != null
                    && !commentArea.getText().trim().isEmpty())
                    ? commentArea.getText()
                    : null;

            appealFacade.approveAppeal(appeal.getAppealID(), remarks);
            showPopupAndRemoveCard("Appeal approved!");
        });
    }

    /**
     * Handles appeal denial.
     */
    @FXML
    private void handleAppealDeny() {
        if (appeal == null) {
            return;
        }

            if (commentArea == null || commentArea.getText().trim().isEmpty()) {
                showError("Please enter remarks before denying.");
                return;
            }

            String remarks = commentArea.getText();

    showConfirmation("/view/dialogs/deniedAppealConfirmation.fxml", () -> {
                appealFacade.denyAppeal(appeal.getAppealID(), remarks);
                showPopupAndRemoveCard("Appeal denied!");
            });
        }

    /**
     * Popup label.
     */
    @FXML
    private Label popupLabel;

    /**
     * Shows popup and removes card after delay.
     * @param message remove card and show message.
     */
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

    /**
     * Current appeal status.
     */
    private String status;

    /**
     * Sets the appeal status.
     * @param pstatus current status of the appeal
     */
    public void setStatus(String pstatus) {
        this.status = pstatus;
    }

    /**
     * Displays a confirmation popup before performing an action.
     * @param fxmlPath path to the confirmation dialog FXML file
     * @param onConfirmAction action to execute when confirmed
     */
    private void showConfirmation(String fxmlPath, Runnable onConfirmAction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource(fxmlPath));
            StackPane popupRoot = loader.load();

            AppealConfirmationController controller = loader.getController();
            controller.setOnConfirm(onConfirmAction);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(popupRoot));
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Error label.
     */
    @FXML
    private Label errorLabel;

    /**
     * Displays an error message temporarily.
     * @param message show error message.
     */
    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            if (errorLabel != null) {
                errorLabel.setVisible(false);
            }
        });
        delay.play();
    }
}
