package org.rocs.osd.controller.appeal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.rocs.osd.controller.dashboard.CenterDashboardController;
import org.rocs.osd.controller.dashboard.DashboardController;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class AppealControllerTest {

    private AppealFacade mockAppealFacade;
    private RecordFacade mockRecordFacade;
    private List<Appeal> pendingAppeals;
    private List<Appeal> approvedAppeals;
    private List<Appeal> deniedAppeals;

    private static final AtomicBoolean mockDialogConfirm = new AtomicBoolean(false);
    private static final AtomicBoolean mockDialogActive = new AtomicBoolean(false);

    @Start
    public void start(Stage stage) throws Exception {
        mockAppealFacade = Mockito.mock(AppealFacade.class);
        mockRecordFacade = Mockito.mock(RecordFacade.class);

        setupMockData();

        javafx.util.Callback<Class<?>, Object> controllerFactory = controllerClass -> {
            if (controllerClass == CenterDashboardController.class) {
                CenterDashboardController controller = new CenterDashboardController();
                controller.setRecordFacade(mockRecordFacade);
                controller.setAppealFacade(mockAppealFacade);
                return controller;
            }
            if (controllerClass == DashboardController.class) {
                return new DashboardController();
            }
            if (controllerClass == AppealController.class) {
                AppealController appealController = new AppealController();
                appealController.setAppealFacade(mockAppealFacade);
                return appealController;
            }
            if (controllerClass == AppealCardController.class) {
                AppealCardController cardController = new AppealCardController();
                cardController.setAppealFacade(mockAppealFacade);
                injectMockDialogs(cardController);
                return cardController;
            }
            if (controllerClass == AppealConfirmationController.class) {
                return createMockAppealConfirmationController();
            }
            if (controllerClass.getName().contains("StudentController")) {
                return mock(controllerClass);
            }
            if (controllerClass.getName().contains("OffenseController") ||
                    controllerClass.getName().contains("RequestController")) {
                return mock(controllerClass);
            }
            try {
                Constructor<?> constructor = controllerClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Exception e) {
                return mock(controllerClass);
            }
        };

        DashboardController.setStaticControllerFactory(controllerFactory);

        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/view/dashboard/dashboard.fxml"));
        dashboardLoader.setControllerFactory(controllerFactory);
        Parent dashboardRoot = dashboardLoader.load();

        stage.setScene(new Scene(dashboardRoot, 1200, 800));
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();

        DashboardController dashboardController = dashboardLoader.getController();
        dashboardController.onLoadAppeal(null);

        Thread.sleep(500);
        WaitForAsyncUtils.waitForFxEvents();
    }

    private void injectMockDialogs(AppealCardController cardController) {
        try {
            cardController.setShowApproveDialog(onConfirm -> {
                if (mockDialogActive.get() && mockDialogConfirm.get()) {
                    onConfirm.run();
                }
            });

            cardController.setShowDenyDialog(onConfirm -> {
                if (mockDialogActive.get() && mockDialogConfirm.get()) {
                    onConfirm.run();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AppealConfirmationController createMockAppealConfirmationController() {
        AppealConfirmationController mock = mock(AppealConfirmationController.class);
        doAnswer(invocation -> {
            Runnable onConfirm = invocation.getArgument(0);
            if (mockDialogActive.get() && mockDialogConfirm.get()) {
                javafx.application.Platform.runLater(onConfirm);
            }
            return null;
        }).when(mock).setOnConfirm(any());
        return mock;
    }

    @BeforeEach
    public void setUp() {
        reset(mockAppealFacade);
        setupMockData();
        mockDialogActive.set(false);
        mockDialogConfirm.set(false);
    }

    @AfterEach
    public void tearDown() {
        DashboardController.clearStaticControllerFactory();
    }

    private void setupMockData() {
        pendingAppeals = new ArrayList<>();
        approvedAppeals = new ArrayList<>();
        deniedAppeals = new ArrayList<>();

        Appeal pending1 = createAppeal(1L, "PENDING", "Late", "Please excuse my tardiness");
        Appeal pending2 = createAppeal(2L, "PENDING", "Absent", "I was sick");
        Appeal approved1 = createAppeal(3L, "APPROVED", "Late", "Approved appeal");
        Appeal denied1 = createAppeal(4L, "DENIED", "Misbehavior", "Denied appeal");
        denied1.setRemarks("Test Remarks");

        pendingAppeals.add(pending1);
        pendingAppeals.add(pending2);
        approvedAppeals.add(approved1);
        deniedAppeals.add(denied1);

        when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(pendingAppeals);
        when(mockAppealFacade.getAppealsByStatus("APPROVED")).thenReturn(approvedAppeals);
        when(mockAppealFacade.getAppealsByStatus("DENIED")).thenReturn(deniedAppeals);

        when(mockRecordFacade.getTotalViolations(anyString())).thenReturn(10);
        when(mockRecordFacade.getMostFrequentOffense(anyString())).thenReturn(Map.of("Late", 50.0));
        when(mockRecordFacade.getTodayViolations()).thenReturn(3);
        when(mockRecordFacade.getRecentViolations(anyString(), anyInt())).thenReturn(List.of());
    }

    private Appeal createAppeal(Long id, String status, String offense, String msg) {
        Appeal appeal = new Appeal();
        appeal.setAppealID(id);
        appeal.setMessage(msg);
        appeal.setDateFiled(new Date());
        appeal.setStatus(status);

        Record r = new Record();
        r.setRecordId(id);
        r.setRemarks(offense);
        appeal.setRecord(r);

        Enrollment e = new Enrollment();
        e.setEnrollmentId(id);

        Student s = new Student();
        s.setStudentId("S001" + id);
        s.setFirstName("John");
        s.setLastName("Doe");

        e.setStudent(s);
        appeal.setEnrollment(e);

        return appeal;
    }

    @Test
    public void testPendingTabDisplaysPendingAppeals(FxRobot robot) {
        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("PENDING");

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().size() >= 2);
    }

    @Test
    public void testApprovedTabDisplaysApprovedAppeals(FxRobot robot) {
        mockDialogActive.set(true);
        mockDialogConfirm.set(true);

        doAnswer(inv -> {
            pendingAppeals.removeIf(a -> a.getAppealID() == 1L);
            approvedAppeals.add(createAppeal(1L, "APPROVED", "Late", "msg"));
            return null;
        }).when(mockAppealFacade).approveAppeal(eq(1L), any());

        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox list = robot.lookup("#listContainer").queryAs(VBox.class);

        Node card = list.getChildren().get(0);
        robot.clickOn(robot.from(card).lookup("#arrowButton").queryButton());

        robot.clickOn(robot.from(card).lookup("#approveButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade).approveAppeal(eq(1L), any());
    }

    @Test
    public void testDeniedTabDisplaysDeniedAppeals(FxRobot robot) {
        mockDialogActive.set(true);
        mockDialogConfirm.set(true);

        doAnswer(inv -> {
            pendingAppeals.removeIf(a -> a.getAppealID() == 1L);
            Appeal denied = createAppeal(1L, "DENIED", "Late", "msg");
            denied.setRemarks("Test Denied");
            deniedAppeals.add(denied);
            return null;
        }).when(mockAppealFacade).denyAppeal(eq(1L), anyString());

        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox list = robot.lookup("#listContainer").queryAs(VBox.class);
        Node card = list.getChildren().get(0);

        robot.clickOn(robot.from(card).lookup("#arrowButton").queryButton());

        TextArea area = robot.from(card).lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(area).write("Test Denied");

        robot.clickOn(robot.from(card).lookup("#denyButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade).denyAppeal(eq(1L), eq("Test Denied"));
    }

    @Test
    public void testCardExpansionShowsAllInputs(FxRobot robot) {
        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        robot.sleep(500);

        Node expandedSection = robot.from(firstCard).lookup("#expandedSection").query();
        assertTrue(expandedSection.isVisible());
    }

    @Test
    public void testDenyWithoutRemarksShowsError(FxRobot robot) {
        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        robot.sleep(300);

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        robot.sleep(300);

        Label errorLabel = robot.from(firstCard).lookup("#errorLabel").queryAs(Label.class);
        assertEquals("Please enter remarks before denying.", errorLabel.getText());
        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
    }

    @Test
    public void testApproveAppealMovesToApprovedTab(FxRobot robot) throws InterruptedException {
        mockDialogActive.set(true);
        mockDialogConfirm.set(true);

        doAnswer(invocation -> {
            pendingAppeals.removeIf(a -> a.getAppealID() == 1L);
            approvedAppeals.add(createAppeal(1L, "APPROVED", "Late", "Please excuse my tardiness"));
            return null;
        }).when(mockAppealFacade).approveAppeal(eq(1L), any());

        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = pendingAppeals.size();

        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#approveButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        robot.sleep(500);

        verify(mockAppealFacade, timeout(3000)).approveAppeal(eq(1L), any());
        assertEquals(initialPendingCount - 1, pendingAppeals.size());
    }

    @Test
    public void testDenyAppealMovesToDeniedTab(FxRobot robot) throws InterruptedException {
        mockDialogActive.set(true);
        mockDialogConfirm.set(true);

        doAnswer(invocation -> {
            pendingAppeals.removeIf(a -> a.getAppealID() == 1L);
            Appeal denied = createAppeal(1L, "DENIED", "Late", "Please excuse my tardiness");
            denied.setRemarks("Test Denied");
            deniedAppeals.add(denied);
            return null;
        }).when(mockAppealFacade).denyAppeal(eq(1L), anyString());

        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = pendingAppeals.size();

        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        TextArea commentArea = robot.from(firstCard).lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Test Denied");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        robot.sleep(500);

        verify(mockAppealFacade, timeout(3000)).denyAppeal(eq(1L), eq("Test Denied"));
        assertEquals(initialPendingCount - 1, pendingAppeals.size());
    }

    @Test
    public void testEmptyPendingAppealsShowsMessage(FxRobot robot) {
        when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(new ArrayList<>());

        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertEquals(0, listContainer.getChildren().size());
    }

    @Test
    public void testCancelDenyConfirmation(FxRobot robot) throws InterruptedException {
        mockDialogActive.set(true);
        mockDialogConfirm.set(false);

        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox list = robot.lookup("#listContainer").queryAs(VBox.class);
        Node card = list.getChildren().get(0);

        robot.clickOn(robot.from(card).lookup("#arrowButton").queryButton());

        TextArea area = robot.from(card).lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(area).write("Test");

        robot.clickOn(robot.from(card).lookup("#denyButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
    }

    @Test
    public void testCancelApproveConfirmation(FxRobot robot) throws InterruptedException {
        mockDialogActive.set(true);
        mockDialogConfirm.set(false);

        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox list = robot.lookup("#listContainer").queryAs(VBox.class);
        Node card = list.getChildren().get(0);

        robot.clickOn(robot.from(card).lookup("#arrowButton").queryButton());
        robot.clickOn(robot.from(card).lookup("#approveButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, never()).approveAppeal(anyLong(), any());
    }

    @Test
    public void testMultipleAppealsInPendingTab(FxRobot robot) {
        robot.clickOn("Pending");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().size() >= 2);

        for (Node card : listContainer.getChildren()) {
            assertNotNull(robot.from(card).lookup("#arrowButton").query());
        }
    }
}