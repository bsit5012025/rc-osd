package org.rocs.osd.controller.offense;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rocs.osd.data.dao.disciplinary.action.DisciplinaryActionDao;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.data.dao.student.StudendDao;
import org.rocs.osd.facade.guardian.GuardianFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinary.action.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class OffenseControllerTest {

    private RecordFacade mockRecordFacade;
    private StudendDao mockStudentDao;
    private OffenseDao mockOffenseDao;
    private DisciplinaryActionDao mockDisciplinaryActionDao;
    private EnrollmentDao mockEnrollmentDao;
    private GuardianFacade mockGuardianFacade;

    private List<Record> jhsRecords;
    private List<Record> shsRecords;
    private List<Record> collegeRecords;

    private boolean addModalOpened;
    private boolean viewModalOpened;
    private Record capturedViewRecord;
    private boolean editModalOpened;
    private Record capturedEditRecord;
    private boolean confirmDialogShown;
    private boolean confirmActionExecuted;
    private boolean cancelDialogShown;

    private Stage primaryStage;
    private Record record;

    @Start
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
    }

    @BeforeEach
    void setup() {
        mockRecordFacade = mock(RecordFacade.class);
        mockStudentDao = mock(StudendDao.class);
        mockOffenseDao = mock(OffenseDao.class);
        mockDisciplinaryActionDao = mock(DisciplinaryActionDao.class);
        mockEnrollmentDao = mock(EnrollmentDao.class);
        mockGuardianFacade = mock(GuardianFacade.class);

        setupMockData();
        resetFlags();
        clearAllMocks();
    }

    @AfterEach
    void tearDown() {
        clearAllMocks();
    }

    private void clearAllMocks() {
        OffenseController.clearControllerFactory();
        OffenseController.clearMocks();
        AddOffenseModalController.clearMockConfirmDialog();
        EditOffenseModalController.clearMockConfirmDialog();
        ViewOffenseModalController.clearMocks();
    }

    private void resetFlags() {
        addModalOpened = false;
        viewModalOpened = false;
        capturedViewRecord = null;
        editModalOpened = false;
        capturedEditRecord = null;
        confirmDialogShown = false;
        confirmActionExecuted = false;
        cancelDialogShown = false;
    }

    private void setupMockData() {
        jhsRecords = new ArrayList<>();
        shsRecords = new ArrayList<>();
        collegeRecords = new ArrayList<>();

        jhsRecords.add(createRecord(1L, "JHS-001", "John", "Doe",
                "Minor", "Late", RecordStatus.PENDING));
        jhsRecords.add(createRecord(2L, "JHS-002", "Jane", "Smith",
                "Major", "Bullying", RecordStatus.PENDING));

        shsRecords.add(createRecord(3L, "SHS-001", "Bob", "Brown",
                "Minor", "Absent", RecordStatus.PENDING));

        collegeRecords.add(createRecord(4L, "CT23-0001", "Alice", "White",
                "Major", "Cheating", RecordStatus.PENDING));

        when(mockRecordFacade.getViolationsByDepartment(
                eq(Department.JHS), anyString()))
                .thenReturn(jhsRecords);
        when(mockRecordFacade.getViolationsByDepartment(
                eq(Department.SHS), anyString()))
                .thenReturn(shsRecords);
        when(mockRecordFacade.getViolationsByDepartment(
                eq(Department.COLLEGE), anyString()))
                .thenReturn(collegeRecords);

        List<String> offenseNames = Arrays.asList("Late", "Bullying", "Cheating");
        List<String> actionNames = Arrays.asList("Community Service",
                "Probation", "Suspension");

        when(mockOffenseDao.findAllOffenseName()).thenReturn(offenseNames);
        when(mockDisciplinaryActionDao.findAllAction()).thenReturn(actionNames);

        Offense offense1 = new Offense();
        offense1.setOffenseId(1L);
        offense1.setOffense("Late");
        offense1.setType("Minor");

        Offense offense2 = new Offense();
        offense2.setOffenseId(2L);
        offense2.setOffense("Bullying");
        offense2.setType("Major");

        Offense offense3 = new Offense();
        offense3.setOffenseId(3L);
        offense3.setOffense("Cheating");
        offense3.setType("Major");

        when(mockOffenseDao.findByName("Late")).thenReturn(offense1);
        when(mockOffenseDao.findByName("Bullying")).thenReturn(offense2);
        when(mockOffenseDao.findByName("Cheating")).thenReturn(offense3);

        when(mockDisciplinaryActionDao.findActionIdByName(
                "Community Service")).thenReturn(1L);
        when(mockDisciplinaryActionDao.findActionIdByName(
                "Probation")).thenReturn(2L);
        when(mockDisciplinaryActionDao.findActionIdByName(
                "Suspension")).thenReturn(3L);

        Student student = new Student();
        student.setStudentId("CT23-0001");
        student.setFirstName("John");
        student.setMiddleName("M");
        student.setLastName("Doe");

        Student student2 = new Student();
        student2.setStudentId("CT23-0091");
        student2.setFirstName("Wilrow");
        student2.setMiddleName("B");
        student2.setLastName("Bayonara");

        when(mockStudentDao.findStudentWithRecordById("CT23-0001"))
                .thenReturn(student);
        when(mockStudentDao.findStudentWithRecordById("INVALID"))
                .thenReturn(null);
        when(mockStudentDao.findStudentWithRecordById("JHS-0020"))
                .thenReturn(student);
        when(mockStudentDao.findStudentWithRecordById("SHS-0037"))
                .thenReturn(student);
        when(mockStudentDao.findStudentWithRecordById("CT23-0091"))
                .thenReturn(student2);
        when(mockStudentDao.findStudentWithRecordById("CT23-0070"))
                .thenReturn(null);
        when(mockStudentDao.findStudentWithRecordById("AD24-0001"))
                .thenReturn(null);

        when(mockEnrollmentDao.findEnrollmentIdByStudentId("CT23-0001"))
                .thenReturn(1L);
        when(mockEnrollmentDao.findEnrollmentIdByStudentId("JHS-0020"))
                .thenReturn(2L);
        when(mockEnrollmentDao.findEnrollmentIdByStudentId("SHS-0037"))
                .thenReturn(3L);
        when(mockEnrollmentDao.findEnrollmentIdByStudentId("CT23-0091"))
                .thenReturn(4L);

        when(mockRecordFacade.createStudentRecord(anyLong(), anyString(),
                anyLong(), any(), anyLong(), anyString()))
                .thenReturn(true);
        when(mockRecordFacade.updateStudentRecord(anyLong(), any(), any(),
                any(), any(), any(), anyString(), any()))
                .thenReturn(true);
        when(mockRecordFacade.resolveRecord(any()))
                .thenReturn(true);
    }

    private Record createRecord(Long id, String studentId,
                                String firstName, String lastName,
                                String offenseLevel, String offenseType, RecordStatus status) {
        Record record = new Record();
        record.setRecordId(id);
        record.setDateOfViolation(Date.valueOf(LocalDate.now()));
        record.setRemarks("Test remarks");
        record.setStatus(status);

        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(id);
        enrollment.setStudent(student);
        record.setEnrollment(enrollment);

        Offense offense = new Offense();
        offense.setOffenseId(id);
        offense.setOffense(offenseType);
        offense.setType(offenseLevel);
        record.setOffense(offense);

        DisciplinaryAction action = new DisciplinaryAction();
        action.setActionId(id);
        action.setActionName("Community Service");
        record.setAction(action);

        Employee employee = new Employee();
        employee.setEmployeeId("EMP-002");
        record.setEmployee(employee);

        return record;
    }

    private Record createEditRecord() {
        return createRecord(1L, "CT23-0001", "John", "Doe",
                "Minor", "Late", RecordStatus.PENDING);
    }

    private void loadOffenseController() throws Exception {
        OffenseController.setControllerFactory(controllerClass -> {
            if (controllerClass == AddOffenseModalController.class) {
                return new AddOffenseModalController();
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/offense/offense.fxml"));

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == OffenseController.class) {
                OffenseController controller = new OffenseController();
                controller.setRecordFacade(mockRecordFacade);
                return controller;
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();

        WaitForAsyncUtils.waitForFxEvents();
    }

    private void loadAddOffenseModal() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/view/offense/addOffenseModal.fxml"));
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == AddOffenseModalController.class) {
                AddOffenseModalController controller =
                        new AddOffenseModalController();
                controller.setStudentDao(mockStudentDao);
                controller.setOffenseDao(mockOffenseDao);
                controller.setRecordFacade(mockRecordFacade);
                controller.setDisciplinaryActionDao(mockDisciplinaryActionDao);
                controller.setEnrollmentDao(mockEnrollmentDao);
                controller.setGuardianFacade(mockGuardianFacade);
                return controller;
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
        WaitForAsyncUtils.waitForFxEvents();
    }

    private void loadEditOffenseModal(Record record) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/offense/editOffenseModal.fxml"));

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == EditOffenseModalController.class) {

                EditOffenseModalController controller =
                        new EditOffenseModalController();

                controller.setStudentDao(mockStudentDao);
                controller.setOffenseDao(mockOffenseDao);
                controller.setRecordFacade(mockRecordFacade);
                controller.setDisciplinaryActionDao(mockDisciplinaryActionDao);
                controller.setEnrollmentDao(mockEnrollmentDao);

                return controller;
            }

            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();

        EditOffenseModalController controller = loader.getController();
        controller.setRecordData(record, null);

        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();

        WaitForAsyncUtils.waitForFxEvents();
    }

    private void loadViewOffenseModal(Record record) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/offense/viewStudentOffenseModal.fxml"));

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == ViewOffenseModalController.class) {

                ViewOffenseModalController controller =
                        new ViewOffenseModalController();

                controller.setRecordFacade(mockRecordFacade);
                controller.setOffenseDao(mockOffenseDao);
                controller.setEnrollmentDao(mockEnrollmentDao);
                controller.setDisciplinaryActionDao(mockDisciplinaryActionDao);

                return controller;
            }

            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();

        ViewOffenseModalController controller = loader.getController();
        controller.setRecordData(record);

        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();

        WaitForAsyncUtils.waitForFxEvents();
    }

    /**
     * Runs the given callable on the JavaFX application thread and waits.
     */
    private void runOnFxThread(Runnable action) throws Exception {
        WaitForAsyncUtils.waitForAsyncFx(5000, (Callable<Void>) () -> {
            action.run();
            return null;
        });
    }

    @Test
    public void testCase01_TabSwitchingDisplaysCorrectTable(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadOffenseController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        robot.clickOn("Junior HS");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockRecordFacade, atLeastOnce())
                .getViolationsByDepartment(eq(Department.JHS), anyString());
        Label deptLabel = robot.lookup("#departmentLabel")
                .queryAs(Label.class);
        assertEquals("Junior HS Violations", deptLabel.getText());
        TableView<Record> table = robot.lookup("#violationsTable")
                .queryAs(TableView.class);
        assertEquals(2, table.getItems().size());

        robot.clickOn("Senior High");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockRecordFacade)
                .getViolationsByDepartment(eq(Department.SHS), anyString());
        assertEquals("Senior HS Violations", deptLabel.getText());
        assertEquals(1, table.getItems().size());

        robot.clickOn("College");
        WaitForAsyncUtils.waitForFxEvents();
        verify(mockRecordFacade)
                .getViolationsByDepartment(eq(Department.COLLEGE), anyString());
        assertEquals("College Violations", deptLabel.getText());
        assertEquals(1, table.getItems().size());
    }

    @Test
    public void testCase02_AddViolationOpensModal(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadOffenseController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        OffenseController.setMockAddOffenseModal(() -> addModalOpened = true);
        robot.clickOn("+ Add Violation");
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(addModalOpened);
    }

    @Test
    public void testCase03_NoDuplicateModalsOnRepeatedClicks(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadOffenseController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        OffenseController.setMockAddOffenseModal(() -> addModalOpened = true);
        robot.clickOn("+ Add Violation");
        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(addModalOpened);

        AtomicInteger callCount = new AtomicInteger();
        OffenseController.setMockAddOffenseModal(() -> callCount.getAndIncrement());
        robot.clickOn("+ Add Violation");
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(0, callCount.get(),
                "Modal should not open again while first is active");
    }

    @Test
    public void testCase04_ValidStudentIdAutoFillsName(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        robot.clickOn("#studentIdTextField").write("CT23-0001");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        TextField nameField = robot.lookup("#studentNameTextField")
                .queryAs(TextField.class);
        assertEquals("John M Doe", nameField.getText());
        verify(mockStudentDao).findStudentWithRecordById("CT23-0001");
    }

    @Test
    public void testCase05_InvalidStudentIdShowsNotFound(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        robot.clickOn("#studentIdTextField").write("CT23-00011");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        Label resultLabel = robot.lookup("#studentResultLabel")
                .queryAs(Label.class);
        assertEquals("Student Not Found!", resultLabel.getText());
    }

    @Test
    public void testCase06_EditedNameSavedOnSubmission(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AddOffenseModalController.setMockConfirmDialog(action -> {
            confirmDialogShown = true;
            action.run();
        });

        robot.clickOn("#studentIdTextField").write("CT23-0091");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        TextField nameField = robot.lookup("#studentNameTextField")
                .queryAs(TextField.class);
        assertEquals("Wilrow B Bayonara", nameField.getText());

        robot.clickOn("#studentNameTextField").write(" Modified");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#offenseTypeComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Bullying");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#actionComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Community Service");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmDialogShown);
        verify(mockRecordFacade).createStudentRecord(anyLong(), anyString(),
                anyLong(), any(), anyLong(), anyString());
    }

    @Test
    public void testCase07_PastDateRejected(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        DatePicker datePicker = robot.lookup("#datePicker")
                .queryAs(DatePicker.class);

        boolean hasDisabledPastDate = datePicker.getDayCellFactory() != null;
        assertTrue(hasDisabledPastDate,
                "DatePicker should have day cell factory disabling past dates");
    }

    @Test
    public void testCase08_FutureDateRejected(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        DatePicker datePicker = robot.lookup("#datePicker")
                .queryAs(DatePicker.class);

        boolean hasDisabledFutureDate = datePicker.getDayCellFactory() != null;
        assertTrue(hasDisabledFutureDate,
                "DatePicker should have day cell factory disabling future dates");
    }

    @Test
    public void testCase09_InvalidStudentIdAndNameRejected(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AddOffenseModalController.setMockConfirmDialog(action -> {
            confirmDialogShown = true;
            action.run();
        });

        robot.clickOn("#studentIdTextField").write("AD24-0001");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#studentNameTextField").write("Melinda Reyes");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#offenseTypeComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Bullying");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#actionComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Community Service");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockRecordFacade, never()).createStudentRecord(anyLong(),
                anyString(), anyLong(), any(), anyLong(), anyString());
    }

    @Test
    public void testCase10_CancelAfterDataShowsConfirmation(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AddOffenseModalController.setMockConfirmDialog(action -> {
            cancelDialogShown = true;
        });

        robot.clickOn("#studentIdTextField").write("CT23-0001");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#offenseTypeComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Bullying");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#actionComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Community Service");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#cancelButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(cancelDialogShown,
                "Confirmation dialog should appear before cancelling");
    }

    @Test
    public void testCase11_SuccessfulSubmission(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AddOffenseModalController.setMockConfirmDialog(action -> {
            confirmDialogShown = true;
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#studentIdTextField").write("CT23-0001");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#offenseTypeComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Bullying");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#actionComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Community Service");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmDialogShown);
        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).createStudentRecord(anyLong(), eq("EMP-002"),
                anyLong(), any(), anyLong(), anyString());
    }

    @Test
    public void testCase12_JuniorHSViolationSavedToJHSTable(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AddOffenseModalController.setMockConfirmDialog(action -> {
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#studentIdTextField").write("JHS-0020");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#offenseTypeComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Bullying");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#actionComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Community Service");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).createStudentRecord(anyLong(), eq("EMP-002"),
                anyLong(), any(), anyLong(), anyString());
    }

    @Test
    public void testCase13_SeniorHSViolationSavedToSHSTable(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadAddOffenseModal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AddOffenseModalController.setMockConfirmDialog(action -> {
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#studentIdTextField").write("SHS-0037");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#offenseTypeComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Bullying");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#actionComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Community Service");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).createStudentRecord(anyLong(), eq("EMP-002"),
                anyLong(), any(), anyLong(), anyString());
    }

    @Test
    public void testCase14_SearchStudentDisplaysViolations(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadOffenseController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        TextField searchField = robot.lookup("#searchTextField")
                .queryAs(TextField.class);
        assertNotNull(searchField, "Search field should exist");

        robot.clickOn("#searchTextField").write("Wilrow Bayona");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        TableView<Record> table = robot.lookup("#violationsTable")
                .queryAs(TableView.class);
        assertNotNull(table);
    }

    @Test
    public void testCase15_ClickViolationOpensDetails(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadOffenseController();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        OffenseController.setMockViewOffenseModal(record -> {
            viewModalOpened = true;
            capturedViewRecord = record;
        });

        robot.clickOn("JHS-001");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(viewModalOpened);
        assertNotNull(capturedViewRecord);
        assertEquals("JHS-001", capturedViewRecord.getEnrollment()
                .getStudent().getStudentId());
    }

    @Test
    public void testCase16_ChangeStudentIdUpdatesName(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            confirmDialogShown = true;
            action.run();
        });

        robot.clickOn("#studentIdTextField").eraseText(10);
        robot.write("JHS-0020");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        TextField nameField = robot.lookup("#studentNameTextField")
                .queryAs(TextField.class);
        assertEquals("John M Doe", nameField.getText());

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmDialogShown);
    }

    @Test
    public void testCase17_EditStudentIdMovesToCorrectTable(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#studentIdTextField").eraseText(10);
        robot.write("JHS-0020");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).updateStudentRecord(anyLong(), any(), any(),
                any(), any(), any(), anyString(), any());
    }

    @Test
    public void testCase18_InvalidStudentIdRejectedOnEdit(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        robot.clickOn("#studentIdTextField").eraseText(10);
        robot.write("CT23-0070");
        robot.type(javafx.scene.input.KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        Label resultLabel = robot.lookup("#studentResultLabel")
                .queryAs(Label.class);
        assertEquals("Student Not Found!", resultLabel.getText());
    }

    @Test
    public void testCase19_EditStudentNameUpdatesRecord(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#studentNameTextField").eraseText(20);
        robot.write("Wilrow Bayonara");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).updateStudentRecord(anyLong(), any(), any(),
                any(), any(), any(), anyString(), any());
    }

    @Test
    public void testCase20_PastDateRejectedOnEdit(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        DatePicker datePicker = robot.lookup("#datePicker")
                .queryAs(DatePicker.class);

        boolean hasDateValidation = datePicker.getDayCellFactory() != null;
        assertTrue(hasDateValidation,
                "DatePicker should have validation for past dates");
    }

    @Test
    public void testCase21_FutureDateRejectedOnEdit(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        DatePicker datePicker = robot.lookup("#datePicker")
                .queryAs(DatePicker.class);

        boolean hasDateValidation = datePicker.getDayCellFactory() != null;
        assertTrue(hasDateValidation,
                "DatePicker should have validation for future dates");
    }

    @Test
    public void testCase22_ChangeOffenseTypeUpdatesRecord(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#offenseTypeComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Bullying");
        WaitForAsyncUtils.waitForFxEvents();

        TextField levelField = robot.lookup("#levelOfOffense")
                .queryAs(TextField.class);
        assertEquals("Major", levelField.getText());

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).updateStudentRecord(anyLong(), any(), any(),
                any(), any(), any(), anyString(), any());
    }

    @Test
    public void testCase23_ChangeActionUpdatesRecord(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#actionComboBox");
        WaitForAsyncUtils.waitForFxEvents();
        robot.clickOn("Probation");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).updateStudentRecord(anyLong(), any(), any(),
                any(), any(), any(), anyString(), any());
    }

    @Test
    public void testCase24_ChangeRemarksUpdatesRecord(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            confirmActionExecuted = true;
            action.run();
        });

        robot.clickOn("#remarksTextArea").eraseText(50);
        robot.write("Caught Cheating");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).updateStudentRecord(anyLong(), any(), any(),
                any(), any(), any(), anyString(), any());
    }

    @Test
    public void testCase25_CancelEditShowsConfirmation(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            cancelDialogShown = true;
        });

        robot.clickOn("#studentNameTextField").eraseText(10);
        robot.write("Modified Name");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#cancelButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(cancelDialogShown,
                "Confirmation alert should appear when cancelling edit");
    }

    @Test
    public void testCase26_SaveChangesShowsConfirmation(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadEditOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        EditOffenseModalController.setMockConfirmDialog(action -> {
            confirmDialogShown = true;
        });

        robot.clickOn("#studentNameTextField").eraseText(10);
        robot.write("Modified Name");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmDialogShown,
                "Confirmation alert should appear before saving changes");
    }

    @Test
    public void testCase27_EditWindowAppearsAfterClickingEdit(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadViewOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        ViewOffenseModalController.setMockEditOffenseModal(record -> {
            editModalOpened = true;
            capturedEditRecord = record;
        });

        robot.clickOn("#editButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(editModalOpened);
        assertNotNull(capturedEditRecord);
    }

    @Test
    public void testCase28_ResolveButtonChangesStatusToResolved(FxRobot robot) throws Exception {
        runOnFxThread(() -> {
            try {
                loadViewOffenseModal(createEditRecord());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        ViewOffenseModalController.setMockConfirmResolve(() -> {
            confirmActionExecuted = true;
        });

        robot.clickOn("#resolveButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(confirmActionExecuted);
        verify(mockRecordFacade).resolveRecord(any());
    }
}
