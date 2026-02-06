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
import javafx.stage.StageStyle;
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

    public void onLogin(ActionEvent event){

        LoginFacade loginFacade;
        LoginDao loginDao = new LoginDaoImpl();
        loginFacade = new LoginFacadeImpl(loginDao);

        boolean loginCheck = loginFacade.login(usernameTextField.getText(),passwordField.getText());

        if(usernameTextField.getText().isBlank() == false && passwordField.getText().isBlank()){
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
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onLogout(ActionEvent event){

        try{
            Parent root = FXMLLoader.load(getClass().getResource("/view/login/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setUserData(null);
            System.out.println("Session logout!");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void onAddViolation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/offenseView/addViolationModal.fxml"));
            Parent root = loader.load();

            Stage popUpModal = new Stage();
            popUpModal.initStyle(StageStyle.UNDECORATED);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            popUpModal.initOwner(stage);

            popUpModal.setScene(new Scene(root));
            popUpModal.setResizable(false);

            popUpModal.show();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onCancelAddViolationButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
