package org.rocs.osd.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    TextField usernameTField;
    @FXML
    TextField passwordTField;

    public void toLogin(ActionEvent event){

        if(usernameTField.getText().isBlank() == false && passwordTField.getText().isBlank()){
            System.out.println("Enter unsername and password!");
        }
        else{
            System.out.println("Username: " +usernameTField.getText());
            System.out.println("Password: " +passwordTField.getText());
        }
    }
}
