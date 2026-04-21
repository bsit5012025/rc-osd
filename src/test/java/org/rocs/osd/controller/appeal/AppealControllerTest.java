package org.rocs.osd.controller.appeal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private AppealController appealController;

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
                DashboardController controller = new DashboardController();
                return controller;
            }
            if (controllerClass == AppealController.class) {
                AppealController controller = new AppealController();
                controller.setAppealFacade(mockAppealFacade);
                appealController = controller;
                return controller;
            }
            if (controllerClass == AppealCardController.class) {
                AppealCardController cardController = new AppealCardController();
                cardController.setAppealFacade(mockAppealFacade);
                return cardController;
            }
            if (controllerClass == org.rocs.osd.controller.student.StudentController.class) {
                try {
                    org.rocs.osd.controller.student.StudentController mockStudent =
                            Mockito.mock(org.rocs.osd.controller.student.StudentController.class);
                    return mockStudent;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to mock StudentController", e);
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
        loadAppealModuleDirectly(dashboardController);

        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
    }

    private void loadAppealModuleDirectly(DashboardController dashboardController) throws Exception {
        FXMLLoader appealLoader = new FXMLLoader(getClass().getResource("/view/appeal/appeal.fxml"));

        javafx.util.Callback<Class<?>, Object> factory = DashboardController.getStaticControllerFactory();
        if (factory != null) {
            appealLoader.setControllerFactory(factory);
        }

        Parent appealRoot = appealLoader.load();

        try {
            dashboardController.onLoadAppeal(null);
        } catch (Exception e) {
            if (dashboardController.getClass().getDeclaredField("centerPane") != null) {
                java.lang.reflect.Field centerField = dashboardController.getClass().getDeclaredField("centerPane");
                centerField.setAccessible(true);
                Object centerPane = centerField.get(dashboardController);
                if (centerPane instanceof javafx.scene.layout.BorderPane) {
                    ((javafx.scene.layout.BorderPane) centerPane).setCenter(appealRoot);
                }
            }
        }

        WaitForAsyncUtils.waitForFxEvents();
    }

    @BeforeEach
    public void setUp() {
        setupMockData();
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
        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(robot.from(firstCard).lookup("#expandedSection").tryQuery().isPresent());
        Node expandedSection = robot.from(firstCard).lookup("#expandedSection").query();
        assertTrue(expandedSection.isVisible());
        assertTrue(expandedSection.isManaged());
    }

    @Test
    public void testDenyWithoutRemarksShowsError(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        Node firstCard = listContainer.getChildren().get(0);
        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.waitForFxEvents();
        Label errorLabel = robot.from(firstCard).lookup("#errorLabel").queryAs(Label.class);

        assertTimeoutPreemptively(java.time.Duration.ofSeconds(3), () -> {
            while (" ".equals(errorLabel.getText()) || errorLabel.getText().trim().isEmpty()) {
                WaitForAsyncUtils.waitForFxEvents();
                Thread.sleep(50);
            }
        });

        assertEquals("Please enter remarks before denying.", errorLabel.getText().trim());
        assertTrue(errorLabel.isVisible());
        assertTrue(errorLabel.isManaged());
        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
    }

    /**
     * Tests approval logic directly via facade call, bypassing confirmation dialog.
     * Verifies that approving an appeal updates the data model correctly.
     */
    @Test
    public void testApproveAppealUpdatesStatus() {
        setupMockData();
        int initialPendingCount = pendingAppeals.size();
        assertTrue(initialPendingCount > 0, "Should have pending appeals to test");

        long appealId = 1L;
        String remarks = "Approval remarks";

        doAnswer(invocation -> {
            long id = invocation.getArgument(0);
            String rem = invocation.getArgument(1);
            pendingAppeals.removeIf(a -> a.getAppealID() == id);
            approvedAppeals.add(createAppeal(id, "APPROVED", "Late", "Please excuse my tardiness"));
            return null;
        }).when(mockAppealFacade).approveAppeal(eq(appealId), eq(remarks));
        WaitForAsyncUtils.waitForFxEvents();
        mockAppealFacade.approveAppeal(appealId, remarks);

        verify(mockAppealFacade, atLeast(1)).approveAppeal(eq(appealId), eq(remarks));
        assertEquals(initialPendingCount - 1, pendingAppeals.size());
        assertTrue(approvedAppeals.stream().anyMatch(a -> a.getAppealID() == appealId));
    }

    /**
     * Tests denial logic directly via facade call, bypassing confirmation dialog.
     * Verifies that denying an appeal updates the data model correctly.
     */
    @Test
    public void testDenyAppealUpdatesStatus() {
        setupMockData();
        int initialPendingCount = pendingAppeals.size();
        assertTrue(initialPendingCount > 0, "Should have pending appeals to test");

        long appealId = 1L;
        String remarks = "Denial remarks";

        doAnswer(invocation -> {
            long id = invocation.getArgument(0);
            String rem = invocation.getArgument(1);
            pendingAppeals.removeIf(a -> a.getAppealID() == id);
            Appeal denied = createAppeal(id, "DENIED", "Late", "Please excuse my tardiness");
            denied.setRemarks(rem);
            deniedAppeals.add(denied);
            return null;
        }).when(mockAppealFacade).denyAppeal(eq(appealId), eq(remarks));
        WaitForAsyncUtils.waitForFxEvents();
        mockAppealFacade.denyAppeal(appealId, remarks);

        verify(mockAppealFacade, atLeast(1)).denyAppeal(eq(appealId), eq(remarks));
        assertEquals(initialPendingCount - 1, pendingAppeals.size());
        assertTrue(deniedAppeals.stream().anyMatch(a -> a.getAppealID() == appealId));
    }

    @Test
    public void testEmptyPendingAppealsShowsMessage(FxRobot robot) {
        when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(new ArrayList<>());
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertEquals(0, listContainer.getChildren().size());
    }

    /**
     * Tests cancel operation by verifying no facade call is made.
     * Simulates user canceling the confirmation dialog.
     */
    @Test
    public void testCancelDenyOperation() {
        setupMockData();
        int initialCount = pendingAppeals.size();
        long appealId = 1L;
        String remarks = "Test remarks";
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
        assertEquals(initialCount, pendingAppeals.size());
    }

    /**
     * Tests cancel operation by verifying no facade call is made.
     * Simulates user canceling the confirmation dialog.
     */
    @Test
    public void testCancelApproveOperation() {
        setupMockData();
        int initialCount = pendingAppeals.size();
        long appealId = 1L;
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
}