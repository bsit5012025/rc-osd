package org.rocs.osd.controller.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.rocs.osd.controller.dashboard.CenterDashboardController;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
public class LoginControllerTest {

    private LoginFacade mockLoginFacade;
    private CenterDashboardController mockDashboardController;

    @Start
    public void start(Stage stage) throws IOException {

        mockLoginFacade = Mockito.mock(LoginFacade.class);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/login/login.fxml")
        );

        Scene scene = new Scene(loader.load());
        LoginController controller = loader.getController();
        controller.setLoginFacade(mockLoginFacade);

        LoginController spyController = Mockito.spy(controller);
        mockDashboardController = new CenterDashboardController();
        mockDashboardController.setRecordFacade(Mockito.mock(RecordFacade.class));
        mockDashboardController.setAppealFacade(Mockito.mock(AppealFacade.class));
        spyController.setCenterDashboardController(mockDashboardController);
        Mockito.doNothing().when(spyController).loadDashboard(Mockito.any());
        loader.setController(spyController);

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setupDashboardMocks() throws Exception {
        RecordFacade mockRecordFacade = Mockito.mock(RecordFacade.class);
        Mockito.when(mockRecordFacade.getTotalViolations(Mockito.anyString())).thenReturn(5);
        Mockito.when(mockRecordFacade.getMostFrequentOffense(Mockito.anyString())).thenReturn(Map.of("Late", 50.0));
        Mockito.when(mockRecordFacade.getTodayViolations()).thenReturn(2);
        Mockito.when(mockRecordFacade.getRecentViolations(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(List.of());

        AppealFacade mockAppealFacade = Mockito.mock(AppealFacade.class);
        Mockito.when(mockAppealFacade.getAppealsByStatus("PENDING"))
                .thenReturn(List.of());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard/centerDashboard.fxml"));
        Parent root = loader.load();
        CenterDashboardController dashboardController = loader.getController()  ;
        dashboardController.setRecordFacade(mockRecordFacade);
        dashboardController.setAppealFacade(mockAppealFacade);
        dashboardController.initialize();

    }

    @Test
    public void testValidLoginInput(FxRobot robot) throws InterruptedException {

        when(mockLoginFacade.login("admin", "admin")).thenReturn(true);
        robot.clickOn("#username");
        robot.write("admin");

        Thread.sleep(1000);

        robot.clickOn("#password");
        robot.write("admin");

        Thread.sleep(1000);

        robot.clickOn("#loginButton");
        verifyThat(".dashboardRoot", NodeMatchers.isVisible());
    }

    @Test
    public void testEmptyFields(FxRobot robot) {
        robot.clickOn("#loginButton");
        verifyThat("#loginButton", NodeMatchers.isVisible());
    }

    @Test
    public void testInvalidLogin(FxRobot robot) {

        when(mockLoginFacade.login("wrong", "wrong")).thenReturn(false);
        robot.clickOn("#username");
        robot.write("wrong");

        robot.clickOn("#password");
        robot.write("wrong");

        robot.clickOn("#loginButton");
        verifyThat("#loginButton", NodeMatchers.isVisible());
    }

    @Test
    public void testEmptyPassword(FxRobot robot) {

        when(mockLoginFacade.login("admin", " ")).thenReturn(false);
        robot.clickOn("#username");
        robot.write("admin");

        robot.clickOn("#loginButton");
        verifyThat("#loginButton", NodeMatchers.isVisible());
    }

    @Test
    public void testEmptyUsername(FxRobot robot) {

        when(mockLoginFacade.login(" ", "admin")).thenReturn(false);
        robot.clickOn("#password");
        robot.write("admin");

        robot.clickOn("#loginButton");
        verifyThat("#loginButton", NodeMatchers.isVisible());
    }

    @Test
    public void testInvalidPassword(FxRobot robot) {

        when(mockLoginFacade.login("admin", "wrong")).thenReturn(false);
        robot.clickOn("#username");
        robot.write("admin");

        robot.clickOn("#password");
        robot.write("wrong");

        robot.clickOn("#loginButton");
        verifyThat("#loginButton", NodeMatchers.isVisible());
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
