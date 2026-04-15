package org.rocs.osd.controller.login;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.data.dao.login.impl.LoginDaoImpl;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.facade.login.impl.LoginFacadeImpl;
import org.rocs.osd.model.login.Login;
import org.rocs.osd.session.Session;

import java.io.IOException;

/**
 * Controller responsible for handling user interactions in
 * the Login screen of the Office of Student Discipline.
 *
 * It validates user credentials and manages
 * navigation to the Dashboard view.
 */
public class LoginController {
    /**
     * Stage used to display login error popups.
     */
    private Stage errorStage;
    /**
    * Text field for entering the username .
    */
    @FXML
    private TextField usernameTextField;
    /**
     * Password field for entering the password.
     */
    @FXML
    private PasswordField passwordField;
    /**
     * Text field to display password in plain text when toggled.
     */
    @FXML
    private TextField passwordTextField;
    /**
     * Button used to toggle password visibility.
     */
    @FXML
    private javafx.scene.control.Button togglePasswordButton;

    /**
     * Toggles the visibility of the password input.
     * Shows the password in plain text if currently hidden,
     * and hides it if currently visible.
     */
    @FXML
    void togglePasswordVisibility() {
        if (passwordField == null
                ||
                passwordTextField == null
                ||
                togglePasswordButton == null) {
                return;
        }
        if (passwordField.isVisible()) {
            passwordTextField.setText(passwordField.getText());
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordTextField.setVisible(true);
            passwordTextField.setManaged(true);
            togglePasswordButton.getStyleClass().add("show-icon");
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordTextField.setVisible(false);
            passwordTextField.setManaged(false);
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            togglePasswordButton.getStyleClass().remove("show-icon");
        }
    }
    /**
     * Handles the login process when the Login button is clicked.
     * This method validates the entered username and
     * password and loads the Dashboard if successful.
     * @param event the action event triggered by clicking the login button.
     */
    public void onLogin(ActionEvent event) {
        /*
          This will check if the username or password fields are empty
          and informs the user to fill them up.
         */
        String user = usernameTextField.getText();
        String pass = passwordField.getText();

        if (user == null || user.isBlank() || pass == null || pass.isBlank()) {
            showErrorPopup("Enter both username and password!");
            return;
        }
        /*
          Initialize DAO and Facade for login process.
         */
        LoginDao loginDao = new LoginDaoImpl();
        LoginFacade loginFacade = new LoginFacadeImpl(loginDao);

        Login login = loginDao.findLoginByUsername(user);

        if (login == null || login.getEmployee() == null) {
            showErrorPopup("Invalid username or password!");
            return;
        }
        /*
          This will check if the entered username
          and password are correct.
         */
        boolean loginCheck = loginFacade.login(user, pass);
        try {
            /*
              If the login credentials are correct,
              Dashboard screen will be loaded.
             */
            if (loginCheck) {
                Session.setEmployee(login.getEmployee());
                loadDashboard(event);
            } else {
                /*
                  If the login fails, "Invalid username or password!
                  will be displayed".
                 */
                showErrorPopup("Invalid username or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadDashboard(ActionEvent event) {
        try {
            if (errorStage != null) {
                errorStage.close();
                errorStage = null;
            }
            /*
              This will load the Dashboard screen from the FXML file.
             */
            Parent root = FXMLLoader.load(getClass()
            .getResource("/view/dashboard/dashboard.fxml"));
            /*
              This will get the current window from the button click event.
             */
            Stage stage = (Stage) ((Node)
            event.getSource()).getScene().getWindow();
            /*
              This will display the Dashboard screen in the window.
             */
            double width = stage.getWidth();
            double height = stage.getHeight();
            stage.setScene(new Scene(root, width, height));
            /*
              This will make the window full screen.
             */
            stage.setMaximized(true);
            stage.show();
            /*
              Throw an exception if the Dashboard screen cannot be loaded.
             */
        } catch (LoadException e) {
            System.err.println("Error loading Dashboard");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("A UI component has not been initialized");
        } catch (IOException e) {
            /*
              Throw an exception if the Dashboard screen cannot be loaded.
             */
            throw new RuntimeException(e);
        }
    }
    private void showErrorPopup(String message) {
        try {

            if (errorStage != null && errorStage.isShowing()) {
                errorStage.close();
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
            "/view/dialogs/loginError.fxml"));
            Parent root = loader.load();
            /*
              Sets the login error banner to the bottom-center
              of the window on different screen sizes.
              */
            errorStage = new Stage();

            Label label = (Label) root.lookup(
                    "#lgnErrText");
            if (label != null) {
                label.setText(message);
            }

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            errorStage.setScene(scene);

            errorStage.initStyle(StageStyle.TRANSPARENT);
            errorStage.initModality(Modality.NONE);

            Rectangle2D screenBounds = Screen.getPrimary()
            .getVisualBounds();
            double windowWidth = root.prefWidth(-1);
            double windowHeight = root.prefHeight(-1);

            errorStage.setX((screenBounds.getWidth() - windowWidth) / 2);
            errorStage.setY(screenBounds.getHeight() - windowHeight);

            errorStage.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(
                    event -> {
                        errorStage.close(); }
            );
            delay.play();


        } catch (IOException ioe) {
            System.err.println("Could not load Error Popup: "
            + ioe.getMessage());
            ioe.printStackTrace();
        }
    }

}
