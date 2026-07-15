package org.rocs.osd.controller.appeal;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Window;

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

    /** Callback action to run after an appeal is successfully processed. */
    private Runnable onActionComplete;

    /** Tracks the current expansion state of the card. */
    private boolean isExpanded = false;

    /** Facade used to communicate with the appeal business logic. */
    private AppealFacade appealFacade;

    @FXML
    private Button arrowButton;

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
            popupBox.setManaged(false);
        }

        if (errorLabel != null) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
            errorLabel.setManaged(false);
        }

        if (arrowButton != null) {
            arrowButton.setMinSize(30, 30);
            arrowButton.setPrefSize(30, 30);
        }
    }

    /**
     * Sets the appeal facade for dependency injection.
     * Used for testing to inject mock facades.
     *
     * @param pAppealFacade the facade to use
     */
    public void setAppealFacade(AppealFacade pAppealFacade) {
        this.appealFacade = pAppealFacade;
    }

    /**
     * Gets the appeal facade, creating default implementation if not set.
     *
     * @return the appeal facade
     */
    private AppealFacade getAppealFacade() {
        if (appealFacade == null) {
            appealFacade = new AppealFacadeImpl();
        }
        return appealFacade;
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
     */
    public void setOnActionComplete(Runnable pAction) {
        this.onActionComplete = pAction;
    }

    /**
     * Handles the Approval button click. Opens a confirmation dialog.
     * If confirmed, updates the appeal status to approved.
     */
    @FXML
    public void handleAppealApprove() {
        showConfirmation("Are you sure you want to", "approve this appeal?",
                "Approve", "Cancel", () -> {
                    String remarks = (
                            commentArea != null
                                    && !commentArea.getText().trim().isEmpty())
                            ? commentArea.getText() : null;
                    getAppealFacade().approveAppeal(appeal.getAppealID(), remarks);
                    showPopupAndRemoveCard("Appeal approved!");
                });
    }

    /**
     * Handles the Deny button click. Validates that remarks are provided
     * before opening a confirmation dialog.
     */
    @FXML
    public void handleAppealDeny() {
        if (commentArea == null || commentArea.getText().trim().isEmpty()) {
            showError("Please enter remarks before denying.");
            return;
        }
        showConfirmation("Are you sure you want to", "deny this appeal?",
                "Deny", "Cancel", () -> {
                    getAppealFacade().denyAppeal(
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
            String l1,
            String l2,
            String confirmTxt,
            String cancelTxt,
            Runnable action) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    java.util.Objects.requireNonNull(
                            getClass().getResource("/view/dialogs/confirmation.fxml"),
                            "Cannot find /view/dialogs/confirmation.fxml"
                    )
            );

            StackPane rootNode = loader.load();

            ConfirmationDialogController controller =
                    loader.getController();

            controller.setMessage(l1, l2);

            controller.setButtonLabels(confirmTxt, cancelTxt);

            controller.setOnConfirm(action);

            rootNode.applyCss();
            rootNode.layout();

            Stage stage = new Stage();

            Window owner = null;
            if (arrowButton != null && arrowButton.getScene() != null) {
                owner = arrowButton.getScene().getWindow();
            } else if (expandedSection != null && expandedSection.getScene() != null) {
                owner = expandedSection.getScene().getWindow();
            }
            if (owner != null) {
                stage.initOwner(owner);
            }

            stage.initStyle(StageStyle.UNDECORATED);

            stage.initModality(Modality.APPLICATION_MODAL);

            Scene scene = new Scene(rootNode);

            scene.setFill(null);

            stage.setScene(scene);

            stage.sizeToScene();

            rootNode.applyCss();
            rootNode.layout();

            stage.show();

            rootNode.requestFocus();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to load confirmation dialog",
                    e);
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
            popupBox.setManaged(true);

            popupBox.applyCss();
            popupBox.layout();
        }

        if (onActionComplete != null) {
            onActionComplete.run();
        }
    }

    /**
     * Displays an error message temporarily on the card.
     * @param msg The error message to show.
     */
    private void showError(String msg) {

        if (errorLabel == null) {
            return;
        }

        errorLabel.setText(msg);

        errorLabel.setVisible(true);
        errorLabel.setManaged(true);

        errorLabel.applyCss();
        errorLabel.layout();

        Parent parent = errorLabel.getParent();

        if (parent != null) {
            parent.requestLayout();
            parent.applyCss();
            parent.layout();
        }
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
    public void toggleExpansion() {

        isExpanded = !isExpanded;

        if (expandedSection != null) {
            expandedSection.setVisible(isExpanded);
            expandedSection.setManaged(isExpanded);
        }

        if (actionBar != null) {

            boolean shouldShow =
                    isExpanded
                            && appeal != null
                            && "PENDING".equals(appeal.getStatus());

            actionBar.setVisible(shouldShow);
            actionBar.setManaged(shouldShow);
        }

        updateIcon();

        if (expandedSection != null) {
            expandedSection.applyCss();
            expandedSection.layout();
        }

        if (actionBar != null) {
            actionBar.applyCss();
            actionBar.layout();
        }

        Parent parent = expandedSection != null
                ? expandedSection.getParent()
                : null;

        if (parent != null) {
            parent.requestLayout();
            parent.applyCss();
            parent.layout();
        }
    }

    /**
     * Updates the toggle button icon based on the current expansion state.
     */
    private void updateIcon() {
        if (arrowIcon != null) {
            String path = isExpanded ? "/assets/downButton.png"
                    : "/assets/rightButton.png";
            try {
                Image newImage = new Image(getClass().getResourceAsStream(path));
                if (newImage.isError()) {
                    throw new RuntimeException("Image load error");
                }
                arrowIcon.setImage(newImage);
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
