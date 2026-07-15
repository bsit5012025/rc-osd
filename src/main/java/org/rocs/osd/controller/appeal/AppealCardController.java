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
 * Controller class for managing the UI behavior of an individual Appeal Card.
 */
public class AppealCardController {

    /** Functional interface for showing confirmation dialogs. */
    @FunctionalInterface
    public interface ConfirmationProvider {
        void show(String line1, String line2, String confirmText, String cancelText, Runnable onConfirm, Runnable onCancel);
    }

    /** Default provider that shows a real popup dialog. */
    private static final ConfirmationProvider DEFAULT_PROVIDER = (l1, l2, confirmTxt, cancelTxt, onConfirm, onCancel) -> {
        try {
            FXMLLoader loader = new FXMLLoader(
                    java.util.Objects.requireNonNull(
                            AppealCardController.class.getResource("/view/dialogs/confirmation.fxml"),
                            "Cannot find /view/dialogs/confirmation.fxml"
                    )
            );
            StackPane rootNode = loader.load();
            ConfirmationDialogController controller = loader.getController();
            controller.setMessage(l1, l2);
            controller.setButtonLabels(confirmTxt, cancelTxt);
            controller.setOnConfirm(() -> {
                onConfirm.run();
                try {
                    Stage stage = (Stage) rootNode.getScene().getWindow();
                    stage.close();
                } catch (Exception ignored) {}
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
            throw new RuntimeException("Failed to load confirmation dialog", e);
        }
    };

    private ConfirmationProvider confirmationProvider = DEFAULT_PROVIDER;

    /** Allows tests to inject a mock confirmation provider. */
    public void setConfirmationProvider(ConfirmationProvider provider) {
        this.confirmationProvider = provider != null ? provider : DEFAULT_PROVIDER;
    }


    @FXML private VBox expandedSection;
    @FXML private HBox actionBar;
    @FXML private VBox popupBox;
    @FXML private Label studentIdLabel;
    @FXML private Label studentNameLabel;
    @FXML private Label offenseLabel;
    @FXML private Label reasonLabel;
    @FXML private ImageView arrowIcon;
    @FXML private TextArea commentArea;
    @FXML private Label popupLabel;
    @FXML private Label errorLabel;
    @FXML private Button arrowButton;

    private Appeal appeal;
    private Runnable onActionComplete;
    private boolean isExpanded = false;
    private AppealFacade appealFacade;

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

    public void setAppealFacade(AppealFacade pAppealFacade) {
        this.appealFacade = pAppealFacade;
    }

    private AppealFacade getAppealFacade() {
        if (appealFacade == null) {
            appealFacade = new AppealFacadeImpl();
        }
        return appealFacade;
    }

    public void setAppeal(Appeal pAppeal) {
        this.appeal = pAppeal;
        loadAppealData();
    }

    public void setOnActionComplete(Runnable pAction) {
        this.onActionComplete = pAction;
    }

    @FXML
    public void handleAppealApprove() {
        showConfirmation("Are you sure you want to", "approve this appeal?",
                "Approve", "Cancel",
                () -> {
                    String remarks = (commentArea != null && !commentArea.getText().trim().isEmpty())
                            ? commentArea.getText() : null;
                    getAppealFacade().approveAppeal(appeal.getAppealID(), remarks);
                    showPopupAndRemoveCard("Appeal approved!");
                },
                () -> { /* cancel - do nothing */ });
    }

    @FXML
    public void handleAppealDeny() {
        if (commentArea == null || commentArea.getText().trim().isEmpty()) {
            showError("Please enter remarks before denying.");
            return;
        }
        showConfirmation("Are you sure you want to", "deny this appeal?",
                "Deny", "Cancel",
                () -> {
                    getAppealFacade().denyAppeal(appeal.getAppealID(), commentArea.getText());
                    showPopupAndRemoveCard("Appeal denied!");
                },
                () -> { /* cancel - do nothing */ });
    }

    private void showConfirmation(String l1, String l2, String confirmTxt,
                                  String cancelTxt, Runnable onConfirm, Runnable onCancel) {
        confirmationProvider.show(l1, l2, confirmTxt, cancelTxt, onConfirm, onCancel);
    }

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

    private void showError(String msg) {
        if (errorLabel == null) return;
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

    private void loadAppealData() {
        if (appeal != null) {
            Enrollment e = appeal.getEnrollment();
            Record r = appeal.getRecord();
            Student s = e.getStudent();

            if (studentIdLabel != null) studentIdLabel.setText(s.getStudentId());
            if (studentNameLabel != null) studentNameLabel.setText(s.getFirstName() + " " + s.getLastName());
            if (offenseLabel != null) offenseLabel.setText(r.getRemarks());
            if (reasonLabel != null) reasonLabel.setText(appeal.getMessage());
        }
    }

    @FXML
    public void toggleExpansion() {
        isExpanded = !isExpanded;
        if (expandedSection != null) {
            expandedSection.setVisible(isExpanded);
            expandedSection.setManaged(isExpanded);
        }
        if (actionBar != null) {
            boolean shouldShow = isExpanded && appeal != null && "PENDING".equals(appeal.getStatus());
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
        javafx.scene.Parent parent = expandedSection != null ? expandedSection.getParent() : null;
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

    private void updateIcon() {
        if (arrowIcon != null) {
            String path = isExpanded ? "/assets/downButton.png" : "/assets/rightButton.png";
            try {
                Image newImage = new Image(getClass().getResourceAsStream(path));
                if (newImage.isError()) throw new RuntimeException("Image load error");
                arrowIcon.setImage(newImage);
            } catch (Exception e) {
                arrowIcon.setRotate(isExpanded ? 90 : 0);
            }
        }
    }
}
