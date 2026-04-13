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
import javafx.util.Callback;
import javafx.util.Duration;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.data.dao.login.impl.LoginDaoImpl;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.facade.login.impl.LoginFacadeImpl;
import java.io.IOException;
import java.util.function.Supplier;

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
     * Facade used to retrieve login data from backend.
     */
    private LoginFacade loginFacade;

    /**
     * Handles the mock process of loginControllerTest.
     * @param pLoginFacade set loginFacade on loginControllerTest to mock db.
     */
    public void setLoginFacade(LoginFacade pLoginFacade) {
        this.loginFacade = pLoginFacade;
    }

    /**
     * Handles the login process when the Login button is clicked.
     * This method validates the entered username and
     * password and loads the Dashboard if successful.
     * @param event the action event triggered by clicking the login button.
     */
    public void onLogin(ActionEvent event) {
        if (loginFacade == null) {
            LoginDao loginDao = new LoginDaoImpl();
            loginFacade = new LoginFacadeImpl(loginDao);
        }

        boolean loginCheck = loginFacade.login(
        usernameTextField.getText(), passwordField.getText());

        String user = usernameTextField.getText();
        String pass = passwordField.getText();

        if (user.isBlank() || pass.isBlank()) {
            showErrorPopup("Enter both username and password!");
            return;
        }
        try {
            if (loginCheck) {
                loadDashboard(event);
            } else {
                showErrorPopup("Invalid username or password!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @return Factory for creating dashboard Parent node (testing only).
     */
    private Supplier<Parent> dashboardFactory;

    /**
     *  @return Dashboard factory for test injection (testing only).
     */
    public Supplier<Parent> getDashboardFactory() {
        return dashboardFactory;
    }

    /**
     * Controller factory for dashboard FXML (used for testing).
     */
    private Callback<Class<?>, Object> dashboardControllerFactory;

    /**
     * @return DashboardController factory for test injection (used for testing).
     */
    public Callback<Class<?>, Object> getDashboardControllerFactory() {
        return dashboardControllerFactory;
    }

    /**
     * Sets factory for creating dashboard Parent (testing only).
     *
     * @param factory supplier that provides dashboard root node
     */
    public void setDashboardFactory( Supplier<Parent> factory) {
        this.dashboardFactory = factory;
    }

    /**
     * Sets controller factory for dashboard FXML (testing only).
     *
     * @param factory callback that creates dashboard controllers
     */
    public void setDashboardControllerFactory(
            Callback<Class<?>, Object> factory) {
        this.dashboardControllerFactory = factory;
    }

    void loadDashboard(ActionEvent event) {
        try {
            if (errorStage != null) {
                errorStage.close();
                errorStage = null;
            }

            Parent root;
            if (dashboardFactory != null) {
                root = dashboardFactory.get();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/view/dashboard/dashboard.fxml"));
                if (dashboardControllerFactory != null) {
                    loader.setControllerFactory(dashboardControllerFactory);
                }
                root = loader.load();
            }

            Stage stage = (Stage) ((Node)
                    event.getSource()).getScene().getWindow();

            double width = stage.getWidth();
            double height = stage.getHeight();
            stage.setScene(new Scene(root, width, height));

            stage.setMaximized(true);
            stage.show();

        } catch (LoadException e) {
            System.err.println("Error loading Dashboard");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("A UI component has not been initialized");
        } catch (IOException e) {
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
