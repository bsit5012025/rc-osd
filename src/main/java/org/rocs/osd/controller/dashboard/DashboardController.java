package org.rocs.osd.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


/**
*The DashboardController manages user interactions on the Dashboard screen.
 */
public class DashboardController {

    @FXML
    StackPane mainContentWrapper;
    @FXML
    Button logoutButton;
    /**
     *   This method is used for logout button
     */
    @FXML
    public void onLogout(ActionEvent event){

        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login/login.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(null);
            System.out.println("Session logout!");
            double width = stage.getWidth();
            double height = stage.getHeight();
            stage.setScene(new Scene(root,width,height));
            stage.show();

        } catch (NullPointerException e) {
            System.err.println("Logout Error: login.fxml not found.");
        } catch (IOException e) {
            System.err.println("Logout Error: Failed to parse login.fxml. Check your FXML file.");
        } catch (Exception e) {
            System.err.println("Logout Error: An unexpected error occurred: " + e.getMessage());
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
}
