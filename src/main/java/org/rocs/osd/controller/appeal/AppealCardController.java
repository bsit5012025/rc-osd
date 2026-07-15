package org.rocs.osd.controller.appeal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import org.rocs.osd.controller.dialog.ConfirmationDialogController;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

/**
 * Controller class for managing the UI behavior
 * of an individual Appeal Card.
 */
public class AppealCardController {

    /**
     * Functional interface for showing confirmation dialogs.
     */
    @FunctionalInterface
    public interface ConfirmationProvider {

        /**
         * Shows a confirmation dialog.
         *
         * @param line1       the first line of message
         * @param line2       the second line of message
         * @param confirmText the confirm button text
         * @param cancelText  the cancel button text
         * @param onConfirm   the confirm action
         * @param onCancel    the cancel action
         */
        void show(String line1, String line2, String confirmText,
                  String cancelText, Runnable onConfirm,
                  Runnable onCancel);
    }

    /** Default provider that shows a real popup dialog. */
    private static final ConfirmationProvider DEFAULT_PROVIDER =
            (l1, l2, confirmTxt, cancelTxt, onConfirm, onCancel) -> {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            java.util.Objects.requireNonNull(
                                    AppealCardController.class
                                            .getResource(
                                                    "/view/dialogs/confirmation.fxml"),
                                    "Cannot find confirmation.fxml"
                            )
                    );
                    StackPane rootNode = loader.load();
                    ConfirmationDialogController controller =
                            loader.getController();
                    controller.setMessage(l1, l2);
                    controller.setButtonLabels(confirmTxt, cancelTxt);
                    controller.setOnConfirm(() -> {
                        onConfirm.run();
                        try {
                            Stage stage = (Stage) rootNode
                                    .getScene().getWindow();
                            stage.close();
                        } catch (Exception ignored) {
                        }
                    });
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    Scene scene = new Scene(rootNode);
                    scene.setFill(null);
                    stage.setScene(scene);
                    stage.sizeToScene();
                    stage.show();
                } catch (Exception e) {
                    throw new RuntimeException(
                            "Failed to load confirmation dialog", e);
                }
            };

    /** The confirmation provider instance. */
    private ConfirmationProvider confirmationProvider =
            DEFAULT_PROVIDER;

    /**
     * Allows tests to inject a mock confirmation provider.
     *
     * @param provider the provider to use, or null for default
     */
    public void setConfirmationProvider(
            ConfirmationProvider provider) {
        this.confirmationProvider = provider != null
                ? provider : DEFAULT_PROVIDER;
    }

    /** The expanded section VBox. */
    @FXML
    private VBox expandedSection;
    /** The action bar HBox. */
    @FXML
    private HBox actionBar;
    /** The popup box VBox. */
    @FXML
    private VBox popupBox;
    /** The student ID label. */
    @FXML
    private Label studentIdLabel;
    /** The student name label. */
    @FXML
    private Label studentNameLabel;
    /** The offense label. */
    @FXML
    private Label offenseLabel;
    /** The reason label. */
    @FXML
    private Label reasonLabel;
    /** The arrow icon ImageView. */
    @FXML
    private ImageView arrowIcon;
    /** The comment area TextArea. */
    @FXML
    private TextArea commentArea;
    /** The popup label. */
    @FXML
    private Label popupLabel;
    /** The error label. */
    @FXML
    private Label errorLabel;
    /** The arrow button. */
    @FXML
    private Button arrowButton;

    /** The appeal data. */
    private Appeal appeal;
    /** The action complete callback. */
    private Runnable onActionComplete;
    /** Whether the card is expanded. */
    private boolean isExpanded = false;
    /** The appeal facade. */
    private AppealFacade appealFacade;

    /**
     * Initializes the controller.
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
     * Sets the appeal facade.
     *
     * @param pAppealFacade the facade to use
     */
    public void setAppealFacade(AppealFacade pAppealFacade) {
        this.appealFacade = pAppealFacade;
    }

    /**
     * Gets the appeal facade, creating default if not set.
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
     * Sets the appeal data.
     *
     * @param pAppeal the appeal to display
     */
    public void setAppeal(Appeal pAppeal) {
        this.appeal = pAppeal;
        loadAppealData();
    }

    /**
     * Sets the action complete callback.
     *
     * @param pAction the callback runnable
     */
    public void setOnActionComplete(Runnable pAction) {
        this.onActionComplete = pAction;
    }

    /**
     * Handles the approve appeal action.
     */
    @FXML
    public void handleAppealApprove() {
        showConfirmation(
                "Are you sure you want to",
                "approve this appeal?",
                "Approve",
                "Cancel",
                () -> {
                    String remarks = (commentArea != null
                            && !commentArea.getText()
                            .trim().isEmpty())
                            ? commentArea.getText() : null;
                    getAppealFacade().approveAppeal(
                            appeal.getAppealID(), remarks);
                    showPopupAndRemoveCard("Appeal approved!");
                },
                () -> {
                    /* cancel - do nothing */
                }
        );
    }

    /**
     * Handles the deny appeal action.
     */
    @FXML
    public void handleAppealDeny() {
        if (commentArea == null
                || commentArea.getText().trim().isEmpty()) {
            showError("Please enter remarks before denying.");
            return;
        }
        showConfirmation(
                "Are you sure you want to",
                "deny this appeal?",
                "Deny",
                "Cancel",
                () -> {
                    getAppealFacade().denyAppeal(
                            appeal.getAppealID(),
                            commentArea.getText());
                    showPopupAndRemoveCard("Appeal denied!");
                },
                () -> {
                    /* cancel - do nothing */
                }
        );
    }

    /**
     * Shows a confirmation dialog.
     *
     * @param l1         line 1
     * @param l2         line 2
     * @param confirmTxt confirm text
     * @param cancelTxt  cancel text
     * @param onConfirm  confirm action
     * @param onCancel   cancel action
     */
    private void showConfirmation(String l1, String l2,
                                  String confirmTxt, String cancelTxt,
                                  Runnable onConfirm, Runnable onCancel) {
        confirmationProvider.show(l1, l2, confirmTxt, cancelTxt,
                onConfirm, onCancel);
    }

    /**
     * Shows popup and removes card.
     *
     * @param msg the message to show
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
     * Shows an error message.
     *
     * @param msg the error message
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
        javafx.scene.Parent parent = errorLabel.getParent();
        while (parent != null) {
            parent.requestLayout();
            parent.applyCss();
            parent.layout();
            if (parent.getScene() != null) {
                parent.getScene().getRoot().applyCss();
                parent.getScene().getRoot().layout();
                break;
            }
            parent = parent.getParent();
        }
    }

    /**
     * Loads appeal data into labels.
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
     * Toggles the card expansion.
     */
    @FXML
    public void toggleExpansion() {
        isExpanded = !isExpanded;
        if (expandedSection != null) {
            expandedSection.setVisible(isExpanded);
            expandedSection.setManaged(isExpanded);
        }
        if (actionBar != null) {
            boolean shouldShow = isExpanded && appeal != null
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
        javafx.scene.Parent parent = expandedSection != null
                ? expandedSection.getParent() : null;
        while (parent != null) {
            parent.requestLayout();
            parent.applyCss();
            parent.layout();
            if (parent.getScene() != null) {
                parent.getScene().getRoot().applyCss();
                parent.getScene().getRoot().layout();
                break;
            }
            parent = parent.getParent();
        }
    }

    /**
     * Updates the arrow icon.
     */
    private void updateIcon() {
        if (arrowIcon != null) {
            String path = isExpanded
                    ? "/assets/downButton.png"
                    : "/assets/rightButton.png";
            try {
                Image newImage = new Image(
                        getClass().getResourceAsStream(path));
                if (newImage.isError()) {
                    throw new RuntimeException(
                            "Image load error");
                }
                arrowIcon.setImage(newImage);
            } catch (Exception e) {
                arrowIcon.setRotate(isExpanded ? 90 : 0);
            }
        }
    }
}