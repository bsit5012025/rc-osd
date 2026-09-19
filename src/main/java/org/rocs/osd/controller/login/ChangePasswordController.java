package org.rocs.osd.controller.login;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.rocs.osd.controller.dialog.ErrorDialogController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.data.dao.login.impl.LoginDaoImpl;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.facade.login.impl.LoginFacadeImpl;

import java.io.IOException;
import java.security.SecureRandom;

public class ChangePasswordController {

    /**
     * Stage used to display login error popups.
     */
    private Stage errorStage;

    /**
     * Password field used to enter the user's new password.
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Text field used to display the password when visibility is enabled.
     */
    @FXML
    private TextField passwordTextField;

    /**
     * Password field used to enter the confirmation password.
     */
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Text field used to display the confirmation password when
     * visibility is enabled.
     */
    @FXML
    private TextField confirmPasswordTextField;

    /**
     * Text field used to enter the generated OTP code.
     */
    @FXML
    private TextField otpCodeField;

    /**
     * Button used to toggle password visibility.
     */
    @FXML
    private Button togglePasswordButton;

    /**
     * Button used to toggle confirmation password visibility.
     */
    @FXML
    private Button toggleConfirmPasswordButton;

    /**
     * Button used to generate or resend the OTP.
     */
    @FXML
    private Button sendOtpButton;

    /**
     * Button used to cancel the password change process.
     */
    @FXML
    private Button cancelButton;

    /**
     * Secure random generator used to generate OTP codes.
     */
    private final SecureRandom r = new SecureRandom();

    /**
     * Facade used to handle login-related operations.
     */
    private LoginFacade loginFacade;

    /**
     * Indicates whether an OTP has been generated.
     */
    private boolean otpStatusSend = false;

    /**
     * Timeline used to control the OTP countdown.
     */
    private Timeline otpTimer;

    /**
     * Number of seconds before the OTP expires.
     */
    private int otpSeconds = 30;

    /**
     * Stores the currently generated OTP.
     */
    private String otpString;

    /**
     * Initializes the controller and its required dependencies.
     * note: suppress warning because of false positive pmd error.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void initialize() {
        LoginDao loginDao = new LoginDaoImpl();
        loginFacade = new LoginFacadeImpl(loginDao);
    }

    /**
     * Toggles the visibility of the password field.
     * note: suppress warning because of false positive pmd error.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void togglePasswordVisibility() {

        if (passwordField == null || passwordTextField == null
                || togglePasswordButton == null) {
            return;
        }

        if (passwordField.isVisible()) {

            passwordTextField.setText(passwordField.getText());

            passwordField.setManaged(false);
            passwordField.setVisible(false);

            passwordTextField.setManaged(true);
            passwordTextField.setVisible(true);
            togglePasswordButton.getStyleClass().add("show-icon");

        } else {

            passwordField.setText(passwordTextField.getText());

            passwordTextField.setManaged(false);
            passwordTextField.setVisible(false);

            passwordField.setManaged(true);
            passwordField.setVisible(true);
            togglePasswordButton.getStyleClass().remove("show-icon");
        }
    }

    /**
     * Toggles the visibility of the confirmation password field.
     * note: suppress warning because of false positive pmd error.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void toggleConfirmPasswordVisibility() {

        if (confirmPasswordField == null || confirmPasswordTextField == null
                || toggleConfirmPasswordButton == null) {
            return;
        }

        if (confirmPasswordField.isVisible()) {

            confirmPasswordTextField.setText(
                    confirmPasswordField.getText()
            );

            confirmPasswordField.setManaged(false);
            confirmPasswordField.setVisible(false);

            confirmPasswordTextField.setManaged(true);
            confirmPasswordTextField.setVisible(true);
            toggleConfirmPasswordButton.getStyleClass().add("show-icon");

        } else {

            confirmPasswordField.setText(
                    confirmPasswordTextField.getText()
            );

            confirmPasswordTextField.setManaged(false);
            confirmPasswordTextField.setVisible(false);

            confirmPasswordField.setManaged(true);
            confirmPasswordField.setVisible(true);
            toggleConfirmPasswordButton.getStyleClass().remove("show-icon");
        }
    }

    /**
     * Cancels the password change process and returns to the login page.
     * note: suppress warning because of false positive pmd error.
     *
     * @param event action event triggered by the cancel button
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void cancelChangePassword(ActionEvent event) {
        toLogin(event);
    }

    /**
     * Generates a new OTP and starts the OTP countdown.
     * note: suppress warning because of false positive pmd error.
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void sendOtp() {
        otpStatusSend = true;
        sendOtpButton.setDisable(true);
        cancelButton.setDisable(true);

        otpString = otpGenerator();
        otpTimer();
    }

    /**
     * Validates the entered information and changes the user's password.
     * note: suppress warning because of false positive pmd error.
     *
     * @param event action event triggered by the change password button
     */
    @FXML
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void changePassword(ActionEvent event) {
        String password;
        String confirmedPassword;

        if (passwordField.isVisible()) {
            password = passwordField.getText();
        } else {
            password = passwordTextField.getText();
        }

        if (confirmPasswordField.isVisible()) {
            confirmedPassword = confirmPasswordField.getText();
        } else {
            confirmedPassword = confirmPasswordTextField.getText();
        }

        if (!otpStatusSend) {
            showErrorPopup("No OTP has been generated yet.");
            return;
        }

        if (otpCodeField.getText().isBlank()) {
            showErrorPopup("Pls Enter Given OTP.");
            return;
        }

        if (password.isBlank() || confirmedPassword.isBlank()) {
            showErrorPopup("Password and confirmation should have value");
            return;
        }

        if (!password.equals(confirmedPassword)) {
            showErrorPopup("Password and confirmation password do not match.");
            return;
        }

        boolean otpStatus = loginFacade.changePassword(
                confirmedPassword,
                otpString,
                otpCodeField.getText()
        );

        if (!otpStatus) {
            showErrorPopup("Invalid OTP");
            return;
        }

        toLogin(event);
        otpString = "";
        otpTimer.stop();
    }

    /**
     * Navigates the current window back to the login page.
     *
     * @param event action event used to access the current stage
     */
    private void toLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/login/login.fxml")
            );

            Parent root = loader.load();

            Stage currentStage = (Stage)
                    ((Node) event.getSource()).getScene().getWindow();

            double currentWidth = currentStage.getWidth();
            double currentHeight = currentStage.getHeight();

            Scene scene = new Scene(root, currentWidth, currentHeight);

            currentStage.setScene(scene);

            currentStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the OTP countdown timer and enables OTP resend after expiration.
     */
    private void otpTimer() {
        otpSeconds = 30;
        sendOtpButton.setText("Resend OTP (" + otpSeconds + ")");

        otpTimer = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    otpSeconds--;

                    if (otpSeconds <= 0) {
                        otpTimer.stop();
                        sendOtpButton.setText("Resend OTP");

                        sendOtpButton.setDisable(false);
                        cancelButton.setDisable(false);
                        otpString = "";
                    } else {
                        sendOtpButton.setText(
                                "Resend OTP (" + otpSeconds + ")"
                        );

                    }
                })
        );
        otpTimer.setCycleCount(Timeline.INDEFINITE);
        otpTimer.play();
    }

    /**
     * Generates a six-character random OTP containing letters and numbers.
     *
     * @return generated six-character OTP
     */
    private String otpGenerator() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder otp = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int index = r.nextInt(characters.length());
            otp.append(characters.charAt(index));
        }

        return otp.toString();
    }

    /**
     * Displays a temporary error popup at the bottom of the screen.
     *
     * @param message the message to display in the error dialog
     */
    private void showErrorPopup(String message) {
        try {
            if (errorStage != null && errorStage.isShowing()) {
                errorStage.close();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/dialogs/error.fxml"));
            Parent root = loader.load();

            ErrorDialogController controller = loader.getController();
            controller.setMessage(message);

            errorStage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            errorStage.setScene(scene);

            errorStage.initStyle(StageStyle.TRANSPARENT);
            errorStage.initModality(Modality.NONE);

            Stage mainStage = (Stage) otpCodeField.getScene().getWindow();

            double popupWidth = 400;
            double popupHeight = 70;
            double centerX = mainStage.getX()
                    + (mainStage.getWidth() - popupWidth) / 2;
            double bottomY = mainStage.getY()
                    + mainStage.getHeight() - popupHeight;

            errorStage.setX(centerX);
            errorStage.setY(bottomY);

            errorStage.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(finishEvent -> errorStage.close());
            delay.play();

        } catch (IOException e) {
            System.err.println("Could not load Error Popup: " + e.getMessage());
        }
    }
}
