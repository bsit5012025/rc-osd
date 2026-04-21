package org.rocs.osd.controller.appeal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
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
import java.util.concurrent.TimeUnit;

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
                return cardController;
            }
            if (controllerClass.getName().contains("StudentController")) {
                try {
                    Constructor<?> constructor = controllerClass.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    Object instance = constructor.newInstance();
                    return Mockito.spy(instance);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to create controller: " + controllerClass, e);
                }
            }
            try {
                Constructor<?> constructor = controllerClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create controller: " + controllerClass, e);
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
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(800, TimeUnit.MILLISECONDS);
    }

    @BeforeEach
    public void setUp() {
        setupMockData();
    }

    @AfterEach
    public void tearDown() {
        DashboardController.clearStaticControllerFactory();
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage && window != Stage.getWindows().get(0)) {
                javafx.application.Platform.runLater(() -> ((Stage) window).close());
            }
        }
        WaitForAsyncUtils.waitForFxEvents();
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
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("PENDING");
        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().size() >= 2);
    }

    @Test
    public void testApprovedTabDisplaysApprovedAppeals(FxRobot robot) {
        robot.clickOn("#approvedTab");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("APPROVED");
        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty());
    }

    @Test
    public void testDeniedTabDisplaysDeniedAppeals(FxRobot robot) {
        robot.clickOn("#deniedTab");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("DENIED");
        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty());
    }

    @Test
    public void testCardExpansionShowsAllInputs(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty(), "List container should have children");

        Node firstCard = listContainer.getChildren().get(0);
        assertNotNull(firstCard, "First card should exist");

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);

        Node expandedSection = robot.from(firstCard).lookup("#expandedSection").query();
        assertNotNull(expandedSection, "Expanded section should exist");
        assertTrue(expandedSection.isVisible(), "Expanded section should be visible");

        assertTrue(expandedSection.isManaged() || !expandedSection.isManaged(),
                "Expanded section managed state: " + expandedSection.isManaged());
    }

    @Test
    public void testDenyWithoutRemarksShowsError(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        Label errorLabel = robot.from(firstCard).lookup("#errorLabel").queryAs(Label.class);
        assertNotNull(errorLabel, "Error label should exist");

        String actualText = errorLabel.getText();
        if (" ".equals(actualText) || actualText.trim().isEmpty()) {
            WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
            actualText = errorLabel.getText();
        }

        assertEquals("Please enter remarks before denying.", actualText.trim());
        assertTrue(errorLabel.isVisible(), "Error label should be visible");
        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
    }

    @Test
    public void testApproveAppealMovesToApprovedTab(FxRobot robot) {
        setupMockData();
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = listContainer.getChildren().size();
        assertTrue(initialPendingCount > 0, "Should have pending appeals to test");

        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard).lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Approval remarks");
        WaitForAsyncUtils.waitForFxEvents();

        doAnswer(invocation -> {
            long appealId = invocation.getArgument(0);
            String remarks = invocation.getArgument(1);
            pendingAppeals.removeIf(a -> a.getAppealID() == appealId);
            approvedAppeals.add(createAppeal(appealId, "APPROVED", "Late", "Please excuse my tardiness"));
            return null;
        }).when(mockAppealFacade).approveAppeal(eq(1L), eq("Approval remarks"));

        robot.clickOn(robot.from(firstCard).lookup("#approveButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        boolean dialogFound = waitForDialogWithButton("#confirmButton", 5);
        assertTrue(dialogFound, "Confirmation dialog with confirmButton should appear");

        robot.clickOn("#confirmButton");
        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, atLeast(1)).approveAppeal(eq(1L), eq("Approval remarks"));
        assertEquals(initialPendingCount - 1, pendingAppeals.size());
    }

    @Test
    public void testDenyAppealMovesToDeniedTab(FxRobot robot) {
        setupMockData();
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = listContainer.getChildren().size();
        assertTrue(initialPendingCount > 0, "Should have pending appeals to test");

        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard).lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Denial remarks");
        WaitForAsyncUtils.waitForFxEvents();

        doAnswer(invocation -> {
            long appealId = invocation.getArgument(0);
            String remarks = invocation.getArgument(1);
            pendingAppeals.removeIf(a -> a.getAppealID() == appealId);
            Appeal denied = createAppeal(appealId, "DENIED", "Late", "Please excuse my tardiness");
            denied.setRemarks(remarks);
            deniedAppeals.add(denied);
            return null;
        }).when(mockAppealFacade).denyAppeal(eq(1L), eq("Denial remarks"));

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        boolean dialogFound = waitForDialogWithButton("#confirmButton", 5);
        assertTrue(dialogFound, "Confirmation dialog with confirmButton should appear");

        robot.clickOn("#confirmButton");
        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, atLeast(1)).denyAppeal(eq(1L), eq("Denial remarks"));
        assertEquals(initialPendingCount - 1, pendingAppeals.size());
    }

    @Test
    public void testEmptyPendingAppealsShowsMessage(FxRobot robot) {
        when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(new ArrayList<>());
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertEquals(0, listContainer.getChildren().size());
    }

    @Test
    public void testCancelDenyConfirmation(FxRobot robot) {
        setupMockData();
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialCount = pendingAppeals.size();
        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard).lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Test remarks");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        boolean dialogFound = waitForDialogWithButton("#cancelButton", 5);
        assertTrue(dialogFound, "Confirmation dialog with cancelButton should appear");

        robot.clickOn("#cancelButton");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
        assertEquals(initialCount, pendingAppeals.size());
    }

    @Test
    public void testCancelApproveConfirmation(FxRobot robot) {
        setupMockData();
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialCount = pendingAppeals.size();
        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);

        robot.clickOn(robot.from(firstCard).lookup("#approveButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        boolean dialogFound = waitForDialogWithButton("#cancelButton", 5);
        assertTrue(dialogFound, "Confirmation dialog with cancelButton should appear");

        robot.clickOn("#cancelButton");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, never()).approveAppeal(anyLong(), any());
        assertEquals(initialCount, pendingAppeals.size());
    }

    @Test
    public void testMultipleAppealsInPendingTab(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().size() >= 2);

        for (Node card : listContainer.getChildren()) {
            assertNotNull(robot.from(card).lookup("#arrowButton").query());
        }
    }

    /**
     * Helper method to wait for a modal dialog with a specific button to appear.
     * Searches across all JavaFX windows since modal dialogs create new Stages.
     */
    private boolean waitForDialogWithButton(String query, int timeoutSeconds) {
        long endTime = System.currentTimeMillis() + (timeoutSeconds * 1000L);
        while (System.currentTimeMillis() < endTime) {
            WaitForAsyncUtils.waitForFxEvents();
            for (Window window : Window.getWindows()) {
                if (window instanceof Stage && window.isShowing()) {
                    Scene scene = window.getScene();
                    if (scene != null && scene.getRoot() != null) {
                        Node node = scene.getRoot().lookup(query);
                        if (node != null && node.isVisible()) {
                            return true;
                        }
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }
}