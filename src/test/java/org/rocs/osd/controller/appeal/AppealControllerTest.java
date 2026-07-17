package org.rocs.osd.controller.appeal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class AppealControllerTest {

    private AppealFacade mockAppealFacade;
    private List<Appeal> pendingAppeals;
    private List<Appeal> approvedAppeals;
    private List<Appeal> deniedAppeals;

    @Start
    public void start(Stage stage) throws Exception {
        mockAppealFacade = mock(AppealFacade.class);
        setupMockData();

        AppealController.setControllerFactory(controllerClass -> {
            if (controllerClass == AppealCardController.class) {
                AppealCardController card = new AppealCardController();
                card.setAppealFacade(mockAppealFacade);
                card.setConfirmationProvider((l1, l2, c1, c2, onConfirm, onCancel) -> onConfirm.run());
                return card;
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appeal/appeal.fxml"));
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == AppealController.class) {
                AppealController controller = new AppealController();
                controller.setAppealFacade(mockAppealFacade);
                return controller;
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();
    }

    @BeforeEach
    void setup() {
        setupMockData();
    }

    @AfterEach
    void tearDown() {
        AppealController.clearControllerFactory();
    }

    private void setupMockData() {
        pendingAppeals = new ArrayList<>();
        approvedAppeals = new ArrayList<>();
        deniedAppeals = new ArrayList<>();

        pendingAppeals.add(createAppeal(1L, "PENDING", "Late", "Please excuse my tardiness"));
        pendingAppeals.add(createAppeal(2L, "PENDING", "Absent", "I was sick"));
        approvedAppeals.add(createAppeal(3L, "APPROVED", "Late", "Approved appeal"));
        deniedAppeals.add(createAppeal(4L, "DENIED", "Misbehavior", "Denied appeal"));

        when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(pendingAppeals);
        when(mockAppealFacade.getAppealsByStatus("APPROVED")).thenReturn(approvedAppeals);
        when(mockAppealFacade.getAppealsByStatus("DENIED")).thenReturn(deniedAppeals);
    }

    private Appeal createAppeal(Long id, String status, String offense, String msg) {
        Appeal appeal = new Appeal();
        appeal.setAppealID(id);
        appeal.setMessage(msg);
        appeal.setDateFiled(new Date());
        appeal.setStatus(status);

        Record record = new Record();
        record.setRecordId(id);
        record.setRemarks(offense);
        appeal.setRecord(record);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(id);

        Student student = new Student();
        student.setStudentId("S001" + id);
        student.setFirstName("John");
        student.setLastName("Doe");

        enrollment.setStudent(student);
        appeal.setEnrollment(enrollment);
        return appeal;
    }

    @Test
    public void testPendingTabDisplaysPendingAppeals(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("PENDING");
        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertEquals(2, listContainer.getChildren().size());
    }

    @Test
    public void testApprovedTabDisplaysApprovedAppeals(FxRobot robot) {
        robot.clickOn("#approvedTab");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade).getAppealsByStatus("APPROVED");
        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertEquals(1, listContainer.getChildren().size());
    }

    @Test
    public void testDeniedTabDisplaysDeniedAppeals(FxRobot robot) {
        robot.clickOn("#deniedTab");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade).getAppealsByStatus("DENIED");
        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertEquals(1, listContainer.getChildren().size());
    }

    @Test
    public void testEmptyPendingAppealsShowsEmptyList(FxRobot robot) {
        when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(new ArrayList<>());
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().isEmpty());
    }

    @Test
    public void testCardExpansionShowsInputs(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#arrowButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(robot.lookup("#expandedSection").query().isVisible());
        assertTrue(robot.lookup("#actionBar").query().isVisible());
    }

    @Test
    public void testCardCollapseHidesInputs(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#arrowButton");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#arrowButton");
        WaitForAsyncUtils.waitForFxEvents();
        assertFalse(robot.lookup("#expandedSection").query().isVisible());
        assertFalse(robot.lookup("#actionBar").query().isVisible());
    }

    @Test
    public void testDenyWithoutRemarksShowsError(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#arrowButton");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#denyButton");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
        assertEquals("Please enter remarks before denying.",
                robot.lookup("#errorLabel").queryAs(Label.class).getText());
    }

    @Test
    public void testApproveAppealWithRemarks(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#arrowButton");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#commentArea").write("Approved");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#approveButton");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade).approveAppeal(1L, "Approved");
    }

    @Test
    public void testDenyAppealWithRemarks(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#arrowButton");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#commentArea").write("Denied");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("#denyButton");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade).denyAppeal(1L, "Denied");
    }
}
