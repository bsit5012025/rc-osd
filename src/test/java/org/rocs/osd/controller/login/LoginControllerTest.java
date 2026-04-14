package org.rocs.osd.controller.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.rocs.osd.controller.dashboard.CenterDashboardController;
import org.rocs.osd.controller.dashboard.DashboardController;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
public class LoginControllerTest {

    private LoginFacade mockLoginFacade;
    private RecordFacade mockRecordFacade;
    private AppealFacade mockAppealFacade;

    @Start
    public void start(Stage stage) throws Exception {
        mockLoginFacade = Mockito.mock(LoginFacade.class);
        mockRecordFacade = Mockito.mock(RecordFacade.class);
        mockAppealFacade = Mockito.mock(AppealFacade.class);

        Mockito.when(mockRecordFacade.getTotalViolations(Mockito.anyString())).thenReturn(5);
        Mockito.when(mockRecordFacade.getMostFrequentOffense(Mockito.anyString()))
                .thenReturn(Map.of("Late", 50.0));
        Mockito.when(mockRecordFacade.getTodayViolations()).thenReturn(2);
        Mockito.when(mockRecordFacade.getRecentViolations(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(List.of());

        Mockito.when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(List.of());

        javafx.util.Callback<Class<?>, Object> controllerFactory =
                controllerClass -> {
                    if (controllerClass == CenterDashboardController.class) {
                        CenterDashboardController controller =
                                new CenterDashboardController();
                        controller.setRecordFacade(mockRecordFacade);
                        controller.setAppealFacade(mockAppealFacade);
                        return controller;
                    }
                    if (controllerClass == DashboardController.class) {
                        return new DashboardController();
                    }
                    try {
                        Constructor<?> constructor =
                                controllerClass.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        return constructor.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(
                                "Failed to create controller: " + controllerClass, e);
                    }
                };

        DashboardController.setStaticControllerFactory(controllerFactory);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/login.fxml"));
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == LoginController.class) {
                LoginController loginController = new LoginController();
                loginController.setLoginFacade(mockLoginFacade);
                return loginController;
            }
            return null;
        });
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @AfterEach
    public void tearDown() {
        DashboardController.clearStaticControllerFactory();
    }

    @Test
    public void testValidLoginInput(FxRobot robot) throws InterruptedException {
        when(mockLoginFacade.login("admin", "admin")).thenReturn(true);

        robot.clickOn("#username").write("admin");
        robot.clickOn("#password").write("admin");
        Thread.sleep(500);

        robot.clickOn("#loginButton");
        verifyThat(".root", NodeMatchers.isVisible());
    }

    @Test
    public void testEmptyFields(FxRobot robot) {
        robot.clickOn("#loginButton");
        verifyThat("#loginButton", NodeMatchers.isVisible());
    }

    @Test
    public void testInvalidLogin(FxRobot robot) {
        when(mockLoginFacade.login("wrong", "wrong")).thenReturn(false);

        robot.clickOn("#username").write("wrong");
        robot.clickOn("#password").write("wrong");
        robot.clickOn("#loginButton");

        verifyThat("#loginButton", NodeMatchers.isVisible());
    }

    @Test
    public void testTogglePasswordVisibility(FxRobot robot) throws InterruptedException {
        robot.clickOn("#password").write("admin");
        Thread.sleep(500);

        robot.clickOn("#togglePasswordButton");
        TextField visiblePassword = robot.lookup("#passwordTextField").queryAs(TextField.class);
        assertTrue(visiblePassword.isVisible());

        robot.clickOn("#togglePasswordButton");
        PasswordField hiddenPassword = robot.lookup("#password").queryAs(PasswordField.class);
        assertTrue(hiddenPassword.isVisible());
    }
}
