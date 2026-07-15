package org.rocs.osd.controller.appeal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
            if (controllerClass == org.rocs.osd.controller.student.StudentController.class) {
                return Mockito.mock(org.rocs.osd.controller.student.StudentController.class);
            }
            try {
                Constructor<?> constructor = controllerClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create controller: " + controllerClass, e);
            }
        };

        AppealController.setControllerFactory(controllerFactory);
        DashboardController.setStaticControllerFactory(controllerFactory);

        FXMLLoader dashboardLoader = new FXMLLoader(
                getClass().getResource("/view/dashboard/dashboard.fxml"));
        dashboardLoader.setControllerFactory(controllerFactory);
        Parent dashboardRoot = dashboardLoader.load();

        stage.setScene(new Scene(dashboardRoot, 1200, 800));
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();

        loadAppealModuleDirectly(dashboardLoader.getController());

        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();
    }

    private void loadAppealModuleDirectly(DashboardController dashboardController) throws Exception {
        FXMLLoader appealLoader = new FXMLLoader(
                getClass().getResource("/view/appeal/appeal.fxml"));

        javafx.util.Callback<Class<?>, Object> factory =
                DashboardController.getStaticControllerFactory();
        if (factory != null) {
            appealLoader.setControllerFactory(factory);
        }

        Parent appealRoot = appealLoader.load();

        try {
            dashboardController.onLoadAppeal(null);
        } catch (Exception e) {
            injectIntoCenterPane(dashboardController, appealRoot);
        }

        WaitForAsyncUtils.waitForFxEvents();
    }

    private void injectIntoCenterPane(DashboardController controller, Node content)
            throws Exception {
        java.lang.reflect.Field centerField =
                controller.getClass().getDeclaredField("centerPane");
        centerField.setAccessible(true);
        Object centerPane = centerField.get(controller);
        if (centerPane instanceof javafx.scene.layout.BorderPane) {
            ((javafx.scene.layout.BorderPane) centerPane).setCenter(content);
        }
    }

    @BeforeEach
    public void setUp() {
        setupMockData();
    }

    @AfterEach
    public void tearDown() {
        DashboardController.clearStaticControllerFactory();
        AppealController.clearControllerFactory();
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
        when(mockRecordFacade.getMostFrequentOffense(anyString()))
                .thenReturn(Map.of("Late", 50.0));
        when(mockRecordFacade.getTodayViolations()).thenReturn(3);
        when(mockRecordFacade.getRecentViolations(anyString(), anyInt()))
                .thenReturn(List.of());
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
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("PENDING");

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().size() >= 2,
                "Pending tab should show at least 2 appeals");
    }

    @Test
    public void testApprovedTabDisplaysApprovedAppeals(FxRobot robot) {
        robot.clickOn("#approvedTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("APPROVED");

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty(),
                "Approved tab should not be empty");
    }

    @Test
    public void testDeniedTabDisplaysDeniedAppeals(FxRobot robot) {
        robot.clickOn("#deniedTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("DENIED");

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty(),
                "Denied tab should not be empty");
    }

    @Test
    public void testEmptyPendingAppealsShowsMessage(FxRobot robot) {
        when(mockAppealFacade.getAppealsByStatus("PENDING")).thenReturn(new ArrayList<>());

        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertEquals(0, listContainer.getChildren().size(),
                "Empty pending should show 0 children");
    }

    @Test
    public void testMultipleAppealsInPendingTab(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().size() >= 2,
                "Should have multiple pending appeals");

        for (Node card : listContainer.getChildren()) {
            assertNotNull(robot.from(card).lookup("#arrowButton").query(),
                    "Each card should have an arrow button");
        }
    }

    @Test
    public void testCardExpansionShowsAllInputs(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty(), "Should have pending appeals");

        Node firstCard = listContainer.getChildren().get(0);

        robot.interact(() -> firstCard.requestFocus());
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        assertTrue(robot.from(firstCard).lookup("#expandedSection").tryQuery().isPresent(),
                "Expanded section should be present in DOM after click");

        Node expandedSection = robot.from(firstCard).lookup("#expandedSection").query();
        assertTrue(expandedSection.isVisible(),
                "Expanded section should be visible");
        assertTrue(expandedSection.isManaged(),
                "Expanded section should be managed (participates in layout)");

        assertTrue(robot.from(firstCard).lookup("#actionBar").tryQuery().isPresent(),
                "Action bar should be present");
        Node actionBar = robot.from(firstCard).lookup("#actionBar").query();
        assertTrue(actionBar.isVisible(),
                "Action bar should be visible for pending appeal");
        assertTrue(actionBar.isManaged(),
                "Action bar should be managed for pending appeal");
    }

    @Test
    public void testDenyWithoutRemarksShowsError(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard)
                .lookup("#commentArea").queryAs(TextArea.class);
        robot.interact(() -> {
            commentArea.clear();
            commentArea.setText("");
        });
        WaitForAsyncUtils.waitForFxEvents();

        Button denyButton = robot.from(firstCard).lookup("#denyButton").queryButton();
        assertTrue(denyButton.isVisible(), "Deny button should be visible");
        assertTrue(denyButton.isManaged(), "Deny button should be managed");

        robot.clickOn(denyButton);
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        Label errorLabel = robot.from(firstCard).lookup("#errorLabel").queryAs(Label.class);
        assertEquals("Please enter remarks before denying.",
                errorLabel.getText().trim(),
                "Error label should show validation message");
        assertTrue(errorLabel.isVisible(),
                "Error label should be visible");
        assertTrue(errorLabel.isManaged(),
                "Error label should be managed");
        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
    }

    @Test
    public void testApproveAppealMovesToApprovedTab(FxRobot robot) {
        setupMockData();

        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = listContainer.getChildren().size();
        assertTrue(initialPendingCount > 0, "Should have pending appeals to test");

        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard)
                .lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Approval remarks");
        WaitForAsyncUtils.waitForFxEvents();

        doAnswer(invocation -> {
            long appealId = invocation.getArgument(0);
            pendingAppeals.removeIf(a -> a.getAppealID() == appealId);
            approvedAppeals.add(createAppeal(appealId, "APPROVED", "Late",
                    "Please excuse my tardiness"));
            return null;
        }).when(mockAppealFacade).approveAppeal(eq(1L), eq("Approval remarks"));

        robot.clickOn(robot.from(firstCard).lookup("#approveButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        clickPopupButton(robot, "#confirmButton");

        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, atLeastOnce()).approveAppeal(eq(1L), eq("Approval remarks"));
        assertEquals(initialPendingCount - 1, pendingAppeals.size(),
                "Pending count should decrease by 1");
    }

    @Test
    public void testCancelApproveConfirmation(FxRobot robot) {
        setupMockData();

        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialCount = pendingAppeals.size();

        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        robot.clickOn(robot.from(firstCard).lookup("#approveButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        clickPopupButton(robot, "#cancelButton");

        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, never()).approveAppeal(anyLong(), any());
        assertEquals(initialCount, pendingAppeals.size(),
                "Pending count should remain unchanged after cancel");
    }

    @Test
    public void testDenyAppealMovesToDeniedTab(FxRobot robot) {
        setupMockData();

        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = listContainer.getChildren().size();
        assertTrue(initialPendingCount > 0, "Should have pending appeals to test");

        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard)
                .lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Denial remarks");
        WaitForAsyncUtils.waitForFxEvents();

        doAnswer(invocation -> {
            long appealId = invocation.getArgument(0);
            String remarks = invocation.getArgument(1);
            pendingAppeals.removeIf(a -> a.getAppealID() == appealId);
            Appeal denied = createAppeal(appealId, "DENIED", "Late",
                    "Please excuse my tardiness");
            denied.setRemarks(remarks);
            deniedAppeals.add(denied);
            return null;
        }).when(mockAppealFacade).denyAppeal(eq(1L), eq("Denial remarks"));

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        clickPopupButton(robot, "#confirmButton");

        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, atLeastOnce()).denyAppeal(eq(1L), eq("Denial remarks"));
        assertEquals(initialPendingCount - 1, pendingAppeals.size(),
                "Pending count should decrease by 1");
    }

    @Test
    public void testCancelDenyConfirmation(FxRobot robot) {
        setupMockData();

        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialCount = pendingAppeals.size();

        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard)
                .lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Test remarks");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());

        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1000, TimeUnit.MILLISECONDS);

        clickPopupButton(robot, "#cancelButton");

        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockAppealFacade, never()).denyAppeal(anyLong(), anyString());
        assertEquals(initialCount, pendingAppeals.size(),
                "Pending count should remain unchanged after cancel");
    }

    private Stage findPopupStage(FxRobot robot) {
        for (javafx.stage.Window window : robot.listWindows()) {
            if (window instanceof Stage stage) {
                if (!stage.isShowing()) {
                    continue;
                }
                if (stage.getScene() == null) {
                    continue;
                }

                Parent root = stage.getScene().getRoot();
                Node confirm = root.lookup("#confirmButton");
                Node cancel = root.lookup("#cancelButton");

                if ((confirm != null) || (cancel != null)) {
                    return stage;
                }
            }
        }
        return null;
    }

    private void clickPopupButton(FxRobot robot, String fxId) {
        Stage popup = null;
        long deadline = System.currentTimeMillis() + 10000;
        while (popup == null && System.currentTimeMillis() < deadline) {
            popup = findPopupStage(robot);
            if (popup == null) {
                WaitForAsyncUtils.waitForFxEvents();
                WaitForAsyncUtils.sleep(100, TimeUnit.MILLISECONDS);
            }
        }

        if (popup == null) {
            StringBuilder sb = new StringBuilder("Available windows:");
            for (javafx.stage.Window w : robot.listWindows()) {
                if (w instanceof Stage s) {
                    sb.append(" Stage[style=").append(s.getStyle())
                            .append(",showing=").append(s.isShowing())
                            .append(",scene=").append(s.getScene() != null)
                            .append("]");
                }
            }
            fail("Confirmation popup should have appeared." + sb);
        }

        Stage finalPopup = popup;
        robot.interact(() -> {
            finalPopup.toFront();
            finalPopup.requestFocus();
            if (finalPopup.getScene() != null && finalPopup.getScene().getRoot() != null) {
                finalPopup.getScene().getRoot().requestFocus();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);

        Node buttonNode = popup.getScene().getRoot().lookup(fxId);
        assertNotNull(buttonNode, "Button " + fxId + " not found in confirmation dialog");
        assertTrue(buttonNode instanceof Button, "Node " + fxId + " is not a Button");

        robot.clickOn(buttonNode);
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
    }
}
