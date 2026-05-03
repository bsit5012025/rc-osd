package org.rocs.osd.controller.logout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.rocs.osd.facade.login.LoginFacade;

public class LogoutControllerTest {

    @FXML
    private Button logoutButton;

    private LoginFacade loginFacade;

    public void setLoginFacade(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @FXML
    private void handleLogout() {
        loginFacade.logout();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();


        }
    }
}