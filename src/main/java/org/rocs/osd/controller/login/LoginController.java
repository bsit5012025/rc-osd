package org.rocs.osd.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.data.dao.login.impl.LoginDaoImpl;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.facade.login.impl.LoginFacadeImpl;
import java.io.IOException;

public class LoginController {

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;

    public void onLogin(ActionEvent event) {

        LoginFacade loginFacade;
        LoginDao loginDao = new LoginDaoImpl();
        loginFacade = new LoginFacadeImpl(loginDao);
        boolean loginCheck = loginFacade.login(usernameTextField.getText(), passwordField.getText());
        try {
            if (!usernameTextField.getText().isBlank() && passwordField.getText().isBlank()) {
                System.out.println("Enter username and password!");
                return;
            }
            if (loginCheck) {
                loadDashboard(event);
            } else {
                System.out.println("Invalid username or password!");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    private void loadDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard/dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();
        } catch (LoadException e){
            System.err.println("Error loading Dashboard");
        } catch (NullPointerException e) {
            System.err.println("A UI component has not been initialized");
        } catch (IOException e) {
            System.err.println("Failed to load on next screen");
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

}
