package org.rocs.osd.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Objects;
import javafx.scene.layout.VBox;
import javafx.scene.control.Labeled;


/**
 * Controller responsible for handling user interactions on the Dashboard screen of the Office of Student Discipline (OSD) System.
 * The dashboard acts as the main navigation interface of the OSD.
 * It allows users to load different modules such as Offense, Appeal, Request, and Student records into the main content area.
 *The DashboardController manages user interactions on the Dashboard screen.
 */
public class DashboardController {

    /**
     * Container used to dynamically load different module views (Offense, Appeal, Request, Student) inside the dashboard.
     */
    @FXML
    StackPane mainContentWrapper;
    @FXML
    Button logoutButton;

    @FXML
    private VBox sidebar;

    @FXML
    private boolean sidebarCollapsed = false;
    /**
     * Opens the logout confirmation dialog when the logout button is clicked.
     * This dialog asks the user to confirm whether they want to exit the system or remain logged in.
     * @param event the action event triggered by clicking the logout button.
     *   This method is used for logout button
     */
    @FXML
    public void onLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dialogs/logoutConfirmation.fxml")
            );
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.initOwner(currentStage);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toggleSidebar() {
        if (sidebarCollapsed) {
            sidebar.setPrefWidth(200);
            sidebar.setMinWidth(200);
            sidebar.setMaxWidth(200);
            for (Node node : sidebar.lookupAll(".sidebarItem")) {
                if (node instanceof Labeled button) {
                    button.setText(button.getUserData() != null ? button.getUserData().toString() : button.getText());
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
     * Loads the Offense module view into the dashboard content area.
     *
     * @param event the action event triggered by the Offense navigation button.
     * This method is used to load Offense view inside the dashboard
     */

    @FXML
    public void onLoadOffense(ActionEvent event) {
        try {
            Parent offenseView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/offense/offense.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(offenseView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found at the specified path." + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: Failed to load the Offense view. Check for FXML syntax errors." +  e.getMessage() );
        }
    }

    @FXML
    public void onLoadDashboard(ActionEvent event) {
        try {
            Parent dashboardView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/dashboard/centerDashboard.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(dashboardView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found at the specified path." + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: Failed to load the Offense view. Check for FXML syntax errors." +  e.getMessage() );
        }
    }

    @FXML
    public void onLoadAppeal(ActionEvent event) {
        try {
            Parent appealView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/appeal/appeal.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(appealView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found at the specified path." + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: Failed to load the Offense view. Check for FXML syntax errors." +  e.getMessage() );
        }
    }

    /**
     * Loads the Request module view into the dashboard content area.
     * @param event the action event triggered by the Request navigation button.
     */
    @FXML
    public void onLoadRequest(ActionEvent event) {
        try {
            Parent requestView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/request/request.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(requestView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found at the specified path." + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: Failed to load the Offense view. Check for FXML syntax errors." +  e.getMessage() );
        }
    }

    /**
     * Loads the Student module view into the dashboard content area.
     *
     * @param event the action event triggered by the Student navigation button.
     */
    @FXML
    public void onLoadStudent(ActionEvent event) {
        try {
            Parent studentView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/student/student.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(studentView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found at the specified path." + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: Failed to load the Offense view. Check for FXML syntax errors." +  e.getMessage() );
        }
    }

    /**
     * Logs the user out of the system and redirects them to the login screen.
     * This method closes the logout confirmation dialog and loads the login interface in the main application window.
     * @param event the action event triggered when the user confirms logout.
     */
    public void logout(ActionEvent event) {
        try {
            Stage popupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage mainStage = (Stage) popupStage.getOwner();
            popupStage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/view/login/login.fxml"));
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
            mainStage.setScene(scene);
            mainStage.setMaximized(true);
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the currently opened popup window.
     * @param event the action event triggered by the close button.
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
