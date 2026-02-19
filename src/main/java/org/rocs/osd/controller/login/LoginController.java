package org.rocs.osd.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

// Controller for the Login screen
public class LoginController {

    // This text field is used to enter the username
    @FXML
    TextField usernameTextField;
    // This text field is used to enter the password
    @FXML
    PasswordField passwordField;
    // This method triggered once the Login button is clicked
    public void onLogin(ActionEvent event){

        //Creates Dao and Facade to handle login process
        LoginFacade loginFacade;
        LoginDao loginDao = new LoginDaoImpl();
        loginFacade = new LoginFacadeImpl(loginDao);

        //This will check if the entered username and password are correct
        boolean loginCheck = loginFacade.login(usernameTextField.getText(),passwordField.getText());

        // This will check if the username or password fields are empty and informs the user to fill them up
        if(usernameTextField.getText().isBlank() == false && passwordField.getText().isBlank()){
            System.out.println("Enter unsername and password!");
            return;
        }
        // If the login credentials are correct, Dashboard screen will be loaded
        if(loginCheck){
            loadDashboard(event);
        }
        else{
        // If the login fails, "Invalid username or password! will be displayed
            System.out.println("Invalid username or password!");
        }
    }

    private void loadDashboard(ActionEvent event) {
        try {
            // This will load the Dashboard screen from the FXML file.
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard/dashboard.fxml"));
            // This will get the current window from the button click event.
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // This will display the Dashboard screen in the window.
            stage.setScene(new Scene(root));
            // This will make the window full screen
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            // Throw an exception if the Dashboard screen cannot be loaded
            throw new RuntimeException(e);
        }
    }

}
