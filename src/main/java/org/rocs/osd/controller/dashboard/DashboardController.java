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
 *The DashboardController manages user interactions on the Dashboard screen.
 */
public class DashboardController {

    @FXML
    StackPane mainContentWrapper;
    @FXML
    Button logoutButton;

    @FXML
    private VBox sidebar;

    private boolean sidebarCollapsed = false;
    /**
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

    public void closePopup(ActionEvent event) {
        if (event != null && event.getSource() instanceof Node node) {
            Scene scene = node.getScene();
            if (scene != null && scene.getWindow() instanceof Stage stage) {
                stage.close();
            }
        }
    }

}
