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

public class LoginController {

    @FXML
    TextField usernameTField;
    @FXML
    PasswordField passwordField;


    public void toLogin(ActionEvent event){

        LoginFacade loginFacade;
        LoginDao loginDao = new LoginDaoImpl();
        loginFacade = new LoginFacadeImpl(loginDao);
        boolean loginCheck = loginFacade.login(usernameTField.getText(),passwordField.getText());

        if(usernameTField.getText().isBlank() == false && passwordField.getText().isBlank()){
            System.out.println("Enter unsername and password!");
            return;
        }
        if(loginCheck){
            loadDashboard(event);
        }
        else{
            System.out.println("Invalid username or password!");
        }
    }

    private void loadDashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard/dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
    }
}
