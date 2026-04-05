package org.rocs.osd.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rocs.osd.controller.dialog.ConfirmationDialogController;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for the Dashboard screen of the Office of Student Discipline.
 * Manages module loading, sidebar toggling, and logout/popup actions.
 */
public class DashboardController {

    /** Wrapper for dynamically loading modules inside the dashboard. */
    @FXML
    private StackPane mainContentWrapper;

    /** Logout button in the dashboard UI. */
    @FXML
    private Button logoutButton;

    /** Sidebar container holding navigation buttons. */
    @FXML
    private VBox sidebar;

    /** Flag indicating if the sidebar is currently collapsed. */
    private boolean sidebarCollapsed = false;

    /** Initializes the dashboard controller. */
    @FXML
    public void initialize() {
        loadModule("/view/dashboard/centerDashboard.fxml");
    }

    /**
     * Opens the logout confirmation dialog when the logout button is clicked.
     * @param event ActionEvent triggered by clicking the logout button.
     */
    @FXML
    public void onLogout(ActionEvent event) {
        showConfirmation(
                "Are you sure you ",
                "want to logout?",
                "Logout",
                "Cancel",
                () -> performLogout()
        );
    }

    private void performLogout() {
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Parent root = FXMLLoader.load(
                    getClass().getResource("/view/login/login.fxml"));

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(
                    root, screenBounds.getWidth(), screenBounds.getHeight());

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showConfirmation(
            String l1, String l2, String confirmTxt,
            String cancelTxt, Runnable action) {
        try {
            String path = "/view/dialogs/confirmation.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

            if (loader.getLocation() == null) {
                throw new IOException("Cannot find FXML file at: " + path);
            }
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
            stage.initOwner(logoutButton.getScene().getWindow());
            stage.setScene(new Scene(rootNode));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Toggles the sidebar between collapsed and expanded states. */
    @FXML
    public void toggleSidebar() {
        if (sidebarCollapsed) {
            sidebar.setPrefWidth(200);
            sidebar.setMinWidth(200);
            sidebar.setMaxWidth(200);
            for (Node node : sidebar.lookupAll(".sidebarItem")) {
                if (node instanceof Labeled button) {
                    button.setText(button.getUserData() != null
                            ? button.getUserData().toString()
                            : button.getText());
                }
            }
            sidebarCollapsed = false;
        } else {
            sidebar.setPrefWidth(70);
            sidebar.setMinWidth(70);
            sidebar.setMaxWidth(70);
            for (Node node : sidebar.lookupAll(".sidebarItem")) {
                if (node instanceof Labeled button) {
                    if (button.getUserData() == null) {
                        button.setUserData(button.getText());
                    }
                    button.setText("");
                }
            }
            sidebarCollapsed = true;
        }
    }

    /**
     * Helper method to load different FXML modules into the center wrapper.
     * @param fxmlPath The path to the FXML file.
     */
    private void loadModule(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource(fxmlPath)));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(view);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found at: " + fxmlPath);
        } catch (IOException e) {
            System.err.println("Failed to load view: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * Loads the Offense module into the dashboard content area.
     * @param event ActionEvent triggered by the Offense button.
     */
    @FXML
    public void onLoadOffense(ActionEvent event) {
        loadModule("/view/offense/offense.fxml");
    }

    /**
     * Loads the Dashboard (center) module into the content area.
     * @param event ActionEvent triggered by the Dashboard button.
     */
    @FXML
    public void onLoadDashboard(ActionEvent event) {
        loadModule("/view/dashboard/centerDashboard.fxml");
    }

    /**
     * Loads the Appeal module into the dashboard content area.
     * @param event ActionEvent triggered by the Appeal button.
     */
    @FXML
    public void onLoadAppeal(ActionEvent event) {
        loadModule("/view/appeal/appeal.fxml");
    }

    /**
     * Loads the Request module into the dashboard content area.
     * @param event ActionEvent triggered by the Request button.
     */
    @FXML
    public void onLoadRequest(ActionEvent event) {
        loadModule("/view/request/request.fxml");
    }

    /**
     * Loads the Student module into the dashboard content area.
     * @param event ActionEvent triggered by the Student button.
     */
    @FXML
    public void onLoadStudent(ActionEvent event) {
        loadModule("/view/student/student.fxml");
    }

    /**
     * Logs the user out and redirects to the login screen.
     * @param event ActionEvent triggered on logout confirmation.
     */
    public void logout(ActionEvent event) {
        try {
            Stage popupStage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            Stage mainStage = (Stage) popupStage.getOwner();
            popupStage.close();
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/view/login/login.fxml"));
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(),
                    screenBounds.getHeight());
            mainStage.setScene(scene);
            mainStage.setMaximized(true);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the currently opened popup window.
     * @param event ActionEvent triggered by the close button.
     */
    public void closePopup(ActionEvent event) {
        if (event != null && event.getSource() instanceof Node node) {
            Scene scene = node.getScene();
            if (scene != null && scene.getWindow() instanceof Stage stage) {
                stage.close();
            }
        }
    }
}
