package org.rocs.osd.controller.request;

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
import org.rocs.osd.facade.employee.EmployeeFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.lang.reflect.Constructor;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class RequestControllerTest {

    private RequestFacade mockRequestFacade;
    private EmployeeFacade mockEmployeeFacade;
    private AppealFacade mockAppealFacade;
    private RecordFacade mockRecordFacade;
    private List<Request> pendingRequests;
    private List<Request> approvedRequests;
    private List<Request> deniedRequests;

    @Start
    public void start(Stage stage) throws Exception {
        mockRequestFacade = Mockito.mock(RequestFacade.class);
        mockEmployeeFacade = Mockito.mock(EmployeeFacade.class);
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
            if (controllerClass == RequestController.class) {
                RequestController requestController = new RequestController();
                requestController.setRequestFacade(mockRequestFacade);
                requestController.setEmployeeFacade(mockEmployeeFacade);
                return requestController;
            }
            if (controllerClass == RequestCardController.class) {
                RequestCardController cardController = new RequestCardController();
                cardController.setRequestFacade(mockRequestFacade);
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

        RequestController.setControllerFactory(controllerFactory);
        DashboardController.setStaticControllerFactory(controllerFactory);

        FXMLLoader dashboardLoader = new FXMLLoader(
                getClass().getResource("/view/dashboard/dashboard.fxml"));
        dashboardLoader.setControllerFactory(controllerFactory);
        Parent dashboardRoot = dashboardLoader.load();

        stage.setScene(new Scene(dashboardRoot, 1200, 800));
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();

        loadRequestModuleDirectly(dashboardLoader.getController());

        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();
    }

    private void loadRequestModuleDirectly(DashboardController dashboardController) throws Exception {
        FXMLLoader requestLoader = new FXMLLoader(
                getClass().getResource("/view/request/request.fxml"));

        javafx.util.Callback<Class<?>, Object> factory =
                DashboardController.getStaticControllerFactory();
        if (factory != null) {
            requestLoader.setControllerFactory(factory);
        }

        Parent requestRoot = requestLoader.load();

        try {
            dashboardController.onLoadRequest(null);
        } catch (Exception e) {
            injectIntoCenterPane(dashboardController, requestRoot);
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
        RequestController.clearControllerFactory();
    }

    private void setupMockData() {
        pendingRequests = new ArrayList<>();
        approvedRequests = new ArrayList<>();
        deniedRequests = new ArrayList<>();

        Employee employee = new Employee();
        employee.setEmployeeId("EMP-001");
        employee.setDepartment(Department.COLLEGE);
        employee.setFirstName("John");
        employee.setMiddleName("A");
        employee.setLastName("Doe");

        Request pending1 = createRequest(
                1L,
                "EMP-001",
                "St. Andrew",
                "By Section",
                "Requesting for the conduct record of all students in St. Andrew",
                java.sql.Date.valueOf(LocalDate.now()),
                null,
                RequestStatus.PENDING
        );

        Request pending2 = createRequest(
                2L,
                "EMP-001",
                "Grade 12",
                "By Year Level",
                "Requesting for the conduct records of all Grade 12 students",
                java.sql.Date.valueOf(LocalDate.now()),
                null,
                RequestStatus.PENDING
        );

        Request approved1 = createRequest(
                3L,
                "EMP-001",
                "St. Andrew",
                "By Section",
                "Request approved for conduct records.",
                java.sql.Date.valueOf(LocalDate.now()),
                "Approved",
                RequestStatus.APPROVED
        );

        Request denied1 = createRequest(
                4L,
                "EMP-001",
                "Grade 11",
                "By Year Level",
                "Request denied due to insufficient authorization.",
                java.sql.Date.valueOf(LocalDate.now()),
                "Test Remarks",
                RequestStatus.DENIED
        );

        pendingRequests.add(pending1);
        pendingRequests.add(pending2);
        approvedRequests.add(approved1);
        deniedRequests.add(denied1);

        when(mockRequestFacade.getAllRequestByStatus(RequestStatus.PENDING)).thenReturn(pendingRequests);
        when(mockRequestFacade.getAllRequestByStatus(RequestStatus.APPROVED)).thenReturn(approvedRequests);
        when(mockRequestFacade.getAllRequestByStatus(RequestStatus.DENIED)).thenReturn(deniedRequests);
        when(mockEmployeeFacade.getEmployeeByEmployeeID("EMP-001"))
                .thenReturn(employee);
    }

    private Request createRequest(long requestID, String employeeID,
                                  String details, String type,
                                  String message, Date date,
                                  String remarks, RequestStatus status) {
        Request request = new Request();
        request.setRequestID(requestID);
        request.setEmployeeID(employeeID);
        request.setDetails(details);
        request.setType(type);
        request.setMessage(message);
        request.setDateProcessed(date);
        request.setRemarks(remarks);
        request.setStatus(status);
        return request;
    }

    @Test
    public void testUserCanViewPendingRequestList(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        verify(mockRequestFacade, atLeastOnce()).getAllRequestByStatus(RequestStatus.PENDING);

        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertTrue(listContainer.getChildren().size() >= 2,
                "Pending tab should show at least 2 request");
    }

    @Test
    public void testUserCanViewDeniedRequestList(FxRobot robot) {
        robot.clickOn("#deniedTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        verify(mockRequestFacade, atLeastOnce()).getAllRequestByStatus(RequestStatus.DENIED);

        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty(),
                "Denied tab should not be empty");
    }

    @Test
    public void testUserCanViewApprovedRequestList(FxRobot robot) {
        robot.clickOn("#approvedTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        verify(mockRequestFacade, atLeastOnce()).getAllRequestByStatus(RequestStatus.APPROVED);

        WaitForAsyncUtils.waitForFxEvents();

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty(),
                "Approved tab should not be empty");
    }

    @Test
    public void testVerifyUserCanDenyARequestWithReason(FxRobot robot) {

        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = listContainer.getChildren().size();
        assertTrue(initialPendingCount > 0,
                "Should have pending Request to test");

        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard)
                .lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Denial remarks");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Denial remarks", commentArea.getText());

        doAnswer(invocation -> {
            long requestId = invocation.getArgument(0);
            pendingRequests.removeIf(a -> a.getRequestID() == requestId);
            deniedRequests.add( createRequest(
                    requestId,
                    "EMP-001",
                    "St. Andrew",
                    "By Section",
                    "Requesting for the conduct record of all students in St. Andrew",
                    java.sql.Date.valueOf(LocalDate.now()),
                    "Denial remarks",
                    RequestStatus.DENIED
            ));
            return null;
        }).when(mockRequestFacade).updateRequestStatus(
                eq(1L),
                eq("Denial remarks"),
                eq(RequestStatus.DENIED)
        );

        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        clickPopupButton(robot, "#confirmButton");
        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#deniedTab");
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockRequestFacade, atLeastOnce()).updateRequestStatus(
                eq(1L),
                eq("Denial remarks"),
                eq(RequestStatus.DENIED)
        );
        assertEquals(initialPendingCount - 1, pendingRequests.size(),
                "Pending count should decrease by 1");
    }

    @Test
    public void testVerifyUserCanApproveARequestWithReason(FxRobot robot) {

        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        int initialPendingCount = listContainer.getChildren().size();
        assertTrue(initialPendingCount > 0,
                "Should have pending Request to test");

        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);

        TextArea commentArea = robot.from(firstCard)
                .lookup("#commentArea").queryAs(TextArea.class);
        robot.clickOn(commentArea).write("Approve remarks");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Approve remarks", commentArea.getText());

        doAnswer(invocation -> {
            long requestId = invocation.getArgument(0);
            pendingRequests.removeIf(a -> a.getRequestID() == requestId);
            approvedRequests.add( createRequest(
                    requestId,
                    "EMP-001",
                    "St. Andrew",
                    "By Section",
                    "Requesting for the conduct record of all students in St. Andrew",
                    java.sql.Date.valueOf(LocalDate.now()),
                    "Approve remarks",
                    RequestStatus.APPROVED
            ));
            return null;
        }).when(mockRequestFacade).updateRequestStatus(
                eq(1L),
                eq("Approve remarks"),
                eq(RequestStatus.APPROVED)
        );

        robot.clickOn(robot.from(firstCard).lookup("#approveButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();

        clickPopupButton(robot, "#confirmButton");
        WaitForAsyncUtils.waitForFxEvents();

        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#approvedTab");
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockRequestFacade, atLeastOnce()).updateRequestStatus(
                eq(1L),
                eq("Approve remarks"),
                eq(RequestStatus.APPROVED)
        );
        assertEquals(initialPendingCount - 1, pendingRequests.size(),
                "Pending count should decrease by 1");
    }

    @Test
    public void testVerifyPendingRequestHaveInfo(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        assertFalse(listContainer.getChildren().isEmpty(), "Should have pending request");
        WaitForAsyncUtils.waitForFxEvents();


        Node firstCard = listContainer.getChildren().get(0);

        robot.interact(() -> firstCard.requestFocus());
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(robot.from(firstCard).lookup("#expandedSection").tryQuery().isPresent(),
                "Expanded section should be present in DOM after click");

        Node expandedSection = robot.from(firstCard).lookup("#expandedSection").query();
        assertTrue(expandedSection.isManaged(),
                "Expanded section should be managed (participates in layout)");

        assertTrue(robot.from(firstCard).lookup("#actionBar").tryQuery().isPresent(),
                "Action bar should be present");
        Node actionBar = robot.from(firstCard).lookup("#actionBar").query();
        assertTrue(actionBar.isManaged(),
                "Action bar should be managed for pending request");
    }

    @Test
    public void testDenyWithoutRemarksShowsError(FxRobot robot) {
        robot.clickOn("#pendingTab");
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);

        VBox listContainer = robot.lookup("#listContainer").queryAs(VBox.class);
        Node firstCard = listContainer.getChildren().get(0);

        robot.clickOn(robot.from(firstCard).lookup("#arrowButton").queryButton());
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        TextArea commentArea = robot.from(firstCard)
                .lookup("#commentArea").queryAs(TextArea.class);
        robot.interact(() -> commentArea.clear());
        WaitForAsyncUtils.waitForFxEvents();

        Label errorLabel = robot.from(firstCard).lookup("#errorLabel").queryAs(Label.class);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn(robot.from(firstCard).lookup("#denyButton").queryButton());
        WaitForAsyncUtils.sleep(1, TimeUnit.SECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Please enter remarks before denying.",
                errorLabel.getText().trim(),
                "Error label should show validation message");
        assertTrue(errorLabel.isVisible(),
                "Error label should be visible");
        assertTrue(errorLabel.isManaged(),
                "Error label should be managed");
        verify(mockRequestFacade, never()).updateRequestStatus(
                anyLong(),
                anyString(),
                any(RequestStatus.class)
        );
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

                if (root.lookup("#confirmButton") != null
                        || root.lookup("#cancelButton") != null) {

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
                WaitForAsyncUtils.sleep(200, TimeUnit.MILLISECONDS);
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

        WaitForAsyncUtils.sleep(300, TimeUnit.MILLISECONDS);
        WaitForAsyncUtils.waitForFxEvents();

        Node button = popup.getScene().getRoot().lookup(fxId);
        assertNotNull(button, "Button " + fxId + " not found in confirmation dialog");

        robot.clickOn(button);
        WaitForAsyncUtils.waitForFxEvents();
        WaitForAsyncUtils.sleep(500, TimeUnit.MILLISECONDS);
    }
}