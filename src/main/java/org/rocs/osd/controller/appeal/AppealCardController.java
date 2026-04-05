package org.rocs.osd.controller.appeal;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import org.rocs.osd.controller.dialog.ConfirmationDialogController;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

/**
 * Controller class for managing the UI behavior of an individual Appeal Card.
 * This class handles the display of appeal details, toggling expanded views,
 * and processing approval or denial actions with confirmation dialogs.
 */
public class AppealCardController {

    /** The section of the card that is hidden until expanded. */
    @FXML private VBox expandedSection;

    /** The container for action buttons (Approve/Deny). */
    @FXML private HBox actionBar;

    /** The container for the post-action success popup. */
    @FXML private VBox popupBox;

    /** Label displaying the student's unique identification number. */
    @FXML private Label studentIdLabel;

    /** Label displaying the full name of the student. */
    @FXML private Label studentNameLabel;

    /** Label describing the offense being appealed. */
    @FXML private Label offenseLabel;

    /** Label displaying the reason or message provided in the appeal. */
    @FXML private Label reasonLabel;

    /** Icon indicating whether the card is collapsed or expanded. */
    @FXML private ImageView arrowIcon;

    /** Text area for the administrator to input remarks or comments. */
    @FXML private TextArea commentArea;

    /** Label used to display success messages in the popup box. */
    @FXML private Label popupLabel;

    /** Label used to display validation or error messages. */
    @FXML private Label errorLabel;

    /** The data model representing the appeal. */
    private Appeal appeal;

    /** Callback action to run after
     * an appeal is successfully processed. */
    private Runnable onActionComplete;

    /** Tracks the current expansion state of the card. */
    private boolean isExpanded = false;

    /** The current status string of the appeal. */
    private String status;

    /** Facade used to communicate with the appeal business logic. */
    private AppealFacade appealFacade = new AppealFacadeImpl();

    /**
     * Initializes the controller.
     * Sets default visibility and managed states
     * for UI components to ensure
     * a collapsed state on load.
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
     * Sets the status of the appeal.
     * @param pStatus The status string
     *    (e.g., "PENDING", "APPROVED").
     */
    public void setStatus(String pStatus) {
        this.status = pStatus;
    }

    /**
     * Injects the appeal data into the
     * controller and updates the UI labels.
     * @param pAppeal The Appeal
     * object containing data to display.
     */
    public void setAppeal(Appeal pAppeal) {
        this.appeal = pAppeal;
        loadAppealData();
    }

    /**
     * Configures a callback to be
     * executed once the user
     * finishes an action on this card.
     * @param pAction A Runnable task
     *(typically used to refresh the main list).
     */
    public void setOnActionComplete(Runnable pAction) {
        this.onActionComplete = pAction;
    }

    /**
     * Handles the Approve button click. Opens a confirmation dialog.
     * If confirmed, updates the appeal status to approved.
     */
    @FXML
    private void handleAppealApprove() {
        showConfirmation("Are you sure you want to", "approve this appeal?",
                "Approve", "Cancel", () -> {
                    String remarks = (
                            commentArea != null
                                    && !commentArea.getText().trim().isEmpty())
                            ? commentArea.getText() : null;
                    appealFacade.approveAppeal(appeal.getAppealID(), remarks);
                    showPopupAndRemoveCard("Appeal approved!");
                });
    }

    /**
     * Handles the Deny button click. Validates that remarks are provided
     * before opening a confirmation dialog.
     */
    @FXML
    private void handleAppealDeny() {
        if (commentArea == null || commentArea.getText().trim().isEmpty()) {
            showError("Please enter remarks before denying.");
            return;
        }
        showConfirmation("Are you sure you want to", "deny this appeal?",
                "Deny", "Cancel", () -> {
                    appealFacade.denyAppeal(
                            appeal.getAppealID(), commentArea.getText());
                    showPopupAndRemoveCard("Appeal denied!");
                });
    }

    /**
     * Loads and displays the universal confirmation dialog.
     * @param l1 First line of the message.
     * @param l2 Second line of the message.
     * @param confirmTxt Text for the confirm button.
     * @param cancelTxt Text for the cancel button.
     * @param action The logic to execute if the user confirms.
     */
    private void showConfirmation(
            String l1, String l2, String confirmTxt,
            String cancelTxt, Runnable action) {
        try {
            String path = "/org/rocs/osd/view/dialogs/confirmation.fxml";
            URL resource = getClass().getResource(path);

            if (resource == null) {
                path = "/view/dialogs/confirmation.fxml";
                resource = getClass().getResource(path);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            StackPane rootNode = loader.load();

            ConfirmationDialogController controller = loader.getController();

            if (controller != null) {
                controller.setMessage(l1, l2);
                controller.setButtonLabels(confirmTxt, cancelTxt);
                controller.setOnConfirm(action);
            }

            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(rootNode));
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Popup Error: Could not load confirmation.fxml");
            e.printStackTrace();
        }
    }

    /**
     * Displays a success popup message and triggers the completion callback.
     * @param msg The message to display (e.g., "Appeal approved!").
     */
    private void showPopupAndRemoveCard(String msg) {
        if (popupLabel != null) {
            popupLabel.setText(msg);
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
     * Displays an error message temporarily on the card.
     * @param msg The error message to show.
     */
    private void showError(String msg) {
        if (errorLabel != null) {
            errorLabel.setText(msg);
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

    /**
     * Extracts data from the appeal model and populates the UI labels.
     */
    private void loadAppealData() {
        if (appeal != null) {
            Enrollment e = appeal.getEnrollment();
            Record r = appeal.getRecord();
            Student s = e.getStudent();

            if (studentIdLabel != null) {
                studentIdLabel.setText(s.getStudentId());
            }

            if (studentNameLabel != null) {
                studentNameLabel.setText(
                        s.getFirstName() + " " + s.getLastName());
            }

            if (offenseLabel != null) {
                offenseLabel.setText(r.getRemarks());
            }

            if (reasonLabel != null) {
                reasonLabel.setText(appeal.getMessage());
            }
        }
    }

    /**
     * Toggles the card's visibility between collapsed and expanded states.
     * The action bar is only shown if the appeal status is "PENDING".
     */
    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;

        if (expandedSection != null) {
            expandedSection.setVisible(isExpanded);
            expandedSection.setManaged(isExpanded);
        }

        if (appeal != null && "PENDING".equals(appeal.getStatus())) {
            if (actionBar != null) {
                actionBar.setVisible(isExpanded);
                actionBar.setManaged(isExpanded);
            }
        }

        updateIcon();
    }

    /**
     * Updates the toggle button icon based on the current expansion state.
     */
    private void updateIcon() {
        if (arrowIcon != null) {
            String path = isExpanded ? "/assets/downButton.png"
                    : "/assets/rightButton.png";
            try {
                arrowIcon.setImage(
                        new Image(getClass().getResourceAsStream(path)));
            } catch (Exception e) {
                if (isExpanded) {
                    arrowIcon.setRotate(90);
                } else {
                    arrowIcon.setRotate(0);
                }
            }
        }
    }
}
