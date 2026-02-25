package org.rocs.osd.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

// This controller manages the Dashboard screen
public class DashboardController {

    @FXML
    StackPane mainContentWrapper;

    // This method is used for logout button
    public void onLogout(ActionEvent event){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/login/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(null);
            System.out.println("Session logout!");
            double width = stage.getWidth();
            double height = stage.getHeight();
            stage.setScene(new Scene(root,width,height));
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    // This method is used to load Offense view inside the dashboard
    public void onLoadOffense(ActionEvent event) {
        try {
            Parent offenseView = FXMLLoader.load(getClass().getResource("/view/offense/offense.fxml"));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(offenseView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
