package org.rocs.osd.controller.dashboard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class DashboardControllerTest {

    static {
        try {
            Class.forName("com.sun.glass.ui.monocle.MonoclePlatformFactory");
            System.setProperty("java.awt.headless", "true");
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
        } catch (ClassNotFoundException e) {
        }
    }

    private RecordFacade mockRecordFacade;
    private AppealFacade mockAppealFacade;

    @Start
    public void start(Stage stage) throws Exception {
        mockRecordFacade = mock(RecordFacade.class);
        mockAppealFacade = mock(AppealFacade.class);
        setupMockData();

        DashboardController.setStaticControllerFactory(controllerClass -> {
            if (controllerClass == CenterDashboardController.class) {
                CenterDashboardController controller = new CenterDashboardController();
                controller.setRecordFacade(mockRecordFacade);
                controller.setAppealFacade(mockAppealFacade);
                return controller;
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/dashboard/dashboard.fxml"));
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == DashboardController.class) {
                return new DashboardController();
            }
            var factory = DashboardController.getStaticControllerFactory();
            if (factory != null) {
                try {
                    return factory.call(controllerClass);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();
    }

    @BeforeEach
    void setup() {
        setupMockData();
    }

    @AfterEach
    void tearDown() {
        DashboardController.clearStaticControllerFactory();
    }

    private void setupMockData() {
        String schoolYear = getCurrentSchoolYear();

        when(mockRecordFacade.getTotalViolations(schoolYear)).thenReturn(42);
        when(mockRecordFacade.getTodayViolations()).thenReturn(3);
        when(mockRecordFacade.getRecentViolations(schoolYear, 10))
                .thenReturn(createMockRecords(5));
        when(mockRecordFacade.getMostFrequentOffense(schoolYear))
                .thenReturn(createMockFrequentOffenses());
        when(mockAppealFacade.getAppealsByStatus("PENDING"))
                .thenReturn(createMockAppeals(7));
    }

    private String getCurrentSchoolYear() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        if (month >= 6) {
            return year + "-" + (year + 1);
        } else {
            return (year - 1) + "-" + year;
        }
    }

    private List<Record> createMockRecords(int count) {
        List<Record> records = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Record record = new Record();
            record.setRecordId((long) i);
            record.setDateOfViolation(new Date());
            record.setRemarks("Violation " + i);

            Offense offense = new Offense();
            offense.setType("Minor");
            offense.setOffense("Cheating");
            record.setOffense(offense);

            Student student = new Student();
            student.setStudentId("S00" + i);
            student.setFirstName("John");
            student.setLastName("Doe");

            Enrollment enrollment = new Enrollment();
            enrollment.setEnrollmentId((long) i);
            enrollment.setStudent(student);
            record.setEnrollment(enrollment);

            records.add(record);
        }
        return records;
    }

    private Map<String, Double> createMockFrequentOffenses() {
        Map<String, Double> offenses = new HashMap<>();
        offenses.put("Cheating", 75.0);
        offenses.put("Punching", 65.0);
        offenses.put("Improper Uniform", 55.0);
        offenses.put("Vaping", 42.0);
        offenses.put("PDA", 82.0);
        return offenses;
    }

    private List<Appeal> createMockAppeals(int count) {
        List<Appeal> appeals = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Appeal appeal = new Appeal();
            appeal.setAppealID((long) i);
            appeal.setStatus("PENDING");
            appeal.setMessage("Appeal message " + i);
            appeal.setDateFiled(new Date());

            Record record = new Record();
            record.setRecordId((long) i);
            record.setRemarks("Late");
            appeal.setRecord(record);

            Student student = new Student();
            student.setStudentId("S00" + i);
            student.setFirstName("Jane");
            student.setLastName("Smith");

            Enrollment enrollment = new Enrollment();
            enrollment.setEnrollmentId((long) i);
            enrollment.setStudent(student);
            appeal.setEnrollment(enrollment);

            appeals.add(appeal);
        }
        return appeals;
    }

    @Test
    public void testDashboardLoadsSuccessfully(FxRobot robot) throws InterruptedException {
        assertTrue(
                robot.lookup(".dashboardRoot")
                        .tryQuery()
                        .isPresent(),
                "Dashboard root should be present");

        assertTrue(
                robot.lookup("#sidebar")
                        .tryQuery()
                        .isPresent(),
                "Sidebar should be present");

        assertTrue(
                robot.lookup("#mainContentWrapper")
                        .tryQuery()
                        .isPresent(),
                "Main content wrapper should be present");
        Thread.sleep(1000);
        StackPane wrapper = robot.lookup("#mainContentWrapper")
                .queryAs(StackPane.class);
        assertFalse(wrapper.getChildren().isEmpty(),
                "Center dashboard should be loaded inside wrapper");
    }

    @Test
    public void testDashboardDisplaysCorrectTotalViolations(FxRobot robot) throws InterruptedException {
        Label label = robot.lookup("#totalViolationLabel")
                .queryAs(Label.class);
        Thread.sleep(1000);
        assertNotNull(label, "Total violation label should exist");
        assertEquals("42", label.getText(),
                "Total violations should display mocked value");

        verify(mockRecordFacade, atLeastOnce()).getTotalViolations(anyString());
    }

    @Test
    public void testDashboardDisplaysCorrectPendingAppeals(FxRobot robot) throws InterruptedException {
        Label label = robot.lookup("#pendingAppealsLabel")
                .queryAs(Label.class);
        Thread.sleep(1000);
        assertNotNull(label, "Pending appeals label should exist");
        assertEquals("7", label.getText(),
                "Pending appeals should display mocked value");

        verify(mockAppealFacade, atLeastOnce()).getAppealsByStatus("PENDING");
    }

    @Test
    public void testDashboardDisplaysCorrectOffensesToday(FxRobot robot) throws InterruptedException {
        Label label = robot.lookup("#offensesTodayLabel")
                .queryAs(Label.class);
        Thread.sleep(1000);
        assertNotNull(label, "Offenses today label should exist");
        assertEquals("3", label.getText(),
                "Offenses today should display mocked value");

        verify(mockRecordFacade, atLeastOnce()).getTodayViolations();
    }

    @Test
    public void testDashboardDisplaysRecentViolationsTable(FxRobot robot) throws InterruptedException {
        TableView<Record> table = robot.lookup("#recentViolations")
                .queryAs(TableView.class);
        Thread.sleep(1000);
        assertNotNull(table, "Recent violations table should exist");
        assertEquals(5, table.getItems().size(),
                "Table should display 5 mocked records");

        verify(mockRecordFacade, atLeastOnce())
                .getRecentViolations(anyString(), eq(10));
    }

    @Test
    public void testDashboardDisplaysFrequentOffenses(FxRobot robot) throws InterruptedException {
        VBox container = robot.lookup("#frequentOffenseContainer")
                .queryAs(VBox.class);
        Thread.sleep(1000);
        assertNotNull(container, "Frequent offense container should exist");
        assertEquals(5, container.getChildren().size(),
                "Should display 5 frequent offenses from mock");

        verify(mockRecordFacade, atLeastOnce())
                .getMostFrequentOffense(anyString());
    }

    @Test
    public void testSidebarToggleCollapsesAndExpands(FxRobot robot) throws InterruptedException {
        VBox sidebar = robot.lookup("#sidebar").queryAs(VBox.class);

        robot.clickOn(".menu-header .sidebarItem");
        Thread.sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(70.0, sidebar.getPrefWidth(), 0.1,
                "Sidebar should collapse to 70px");

        robot.clickOn(".menu-header .sidebarItem");
        Thread.sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(200.0, sidebar.getPrefWidth(), 0.1,
                "Sidebar should expand back to 200px");
    }

    @Test
    public void testDashboardReloadsWithinAcceptableTime(FxRobot robot) throws InterruptedException {
        long start = System.nanoTime();

        robot.clickOn("Dashboard");
        Thread.sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();

        long elapsedMillis = (System.nanoTime() - start) / 1_000_000;

        assertTrue(elapsedMillis < 3000,
                "Dashboard reload should complete within 3 seconds, but took "
                        + elapsedMillis + " ms");
    }

    @Test
    public void testDashboardHandlesEmptyRecentViolations(FxRobot robot) throws InterruptedException {
        when(mockRecordFacade.getRecentViolations(anyString(), eq(10)))
                .thenReturn(new ArrayList<>());

        robot.clickOn("Dashboard");
        Thread.sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();

        TableView<Record> table = robot.lookup("#recentViolations")
                .queryAs(TableView.class);
        assertTrue(table.getItems().isEmpty(),
                "Table should be empty when no recent violations");
    }

    @Test
    public void testDashboardHandlesEmptyPendingAppeals(FxRobot robot) throws InterruptedException {
        when(mockAppealFacade.getAppealsByStatus("PENDING"))
                .thenReturn(new ArrayList<>());

        robot.clickOn("Dashboard");
        Thread.sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();

        Label label = robot.lookup("#pendingAppealsLabel")
                .queryAs(Label.class);
        assertEquals("0", label.getText(),
                "Pending appeals should show 0 when empty");
    }

    @Test
    public void testDashboardHandlesEmptyFrequentOffenses(FxRobot robot) throws InterruptedException {
        when(mockRecordFacade.getMostFrequentOffense(anyString()))
                .thenReturn(new HashMap<>());

        robot.clickOn("Dashboard");
        Thread.sleep(1000);
        WaitForAsyncUtils.waitForFxEvents();

        VBox container = robot.lookup("#frequentOffenseContainer")
                .queryAs(VBox.class);
        assertTrue(container.getChildren().isEmpty(),
                "Frequent offense container should be empty when no data");
    }
}
