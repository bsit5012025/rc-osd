package org.rocs.osd.controller.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
@Tag("gui")
public class LoginControllerTest {

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/login/login.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testValidLoginInput(FxRobot robot) throws InterruptedException {

        robot.clickOn("#username");
        robot.write("admin");

        Thread.sleep(1000);

        robot.clickOn("#password");
        robot.write("admin");

        Thread.sleep(1000);

        robot.clickOn("#loginButton");
    }

    @Test
    public void testEmptyFields(FxRobot robot) {
        robot.clickOn("#loginButton");
    }

    @Test
    public void testInvalidLogin(FxRobot robot) {

        robot.clickOn("#username");
        robot.write("wrong");

        robot.clickOn("#password");
        robot.write("wrong");

        robot.clickOn("#loginButton");
    }

    @Test
    public void testEmptyPassword(FxRobot robot) {

        robot.clickOn("#username");
        robot.write("admin");

        robot.clickOn("#loginButton");
    }

    @Test
    public void testEmptyUsername(FxRobot robot) {

        robot.clickOn("#password");
        robot.write("admin");

        robot.clickOn("#loginButton");
    }

    @Test
    public void testInvalidPassword(FxRobot robot) {

        robot.clickOn("#username");
        robot.write("admin");

        robot.clickOn("#password");
        robot.write("wrong");

        robot.clickOn("#loginButton");
    }
    @Test
    public void testTogglePasswordVisibility(FxRobot robot) throws InterruptedException {

        robot.clickOn("#password");
        robot.write("admin");
        Thread.sleep(1000);

        robot.clickOn("#togglePasswordButton");
        Thread.sleep(1000);

        TextField visiblePassword =
                robot.lookup("#passwordTextField").queryAs(TextField.class);

        assertTrue(visiblePassword.isVisible());

        robot.clickOn("#togglePasswordButton");
        Thread.sleep(1000);

        PasswordField hiddenPassword =
                robot.lookup("#password").queryAs(PasswordField.class);

        assertTrue(hiddenPassword.isVisible());

        robot.clickOn("#loginButton");
        Thread.sleep(1000);

        verifyThat("#loginButton", NodeMatchers.isVisible());
    }
}
