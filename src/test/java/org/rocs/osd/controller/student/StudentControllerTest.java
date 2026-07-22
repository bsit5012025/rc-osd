package org.rocs.osd.controller.student;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.model.disciplinary.status.DisciplinaryStatus;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.person.student.guardian.StudentGuardian;
import org.rocs.osd.model.record.Record;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
public class StudentControllerTest {

    private EnrollmentFacade mockEnrollmentFacade;
    private RecordFacade mockRecordFacade;
    private org.rocs.osd.data.dao.guardian.GuardianDao mockGuardianDao;
    private Runnable mockDownloadHandler;

    @Start
    public void start(Stage stage) throws Exception {
        mockEnrollmentFacade = mock(EnrollmentFacade.class);
        mockRecordFacade = mock(RecordFacade.class);
        mockGuardianDao = mock(
                org.rocs.osd.data.dao.guardian.GuardianDao.class);
        mockDownloadHandler = mock(Runnable.class);
        setupMockData();

        StudentController.setControllerFactory(
                controllerClass -> {
                    if (controllerClass
                            == StudentRecordController.class) {
                        StudentRecordController c =
                                new StudentRecordController();
                        c.setRecordFacade(mockRecordFacade);
                        c.setGuardianDao(mockGuardianDao);
                        c.setDownloadHandler(mockDownloadHandler);
                        return c;
                    }
                    try {
                        return controllerClass
                                .getDeclaredConstructor()
                                .newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/view/student/student.fxml"));
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == StudentController.class) {
                StudentController c = new StudentController();
                c.setEnrollmentFacade(mockEnrollmentFacade);
                return c;
            }
            try {
                return controllerClass
                        .getDeclaredConstructor()
                        .newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();
    }

    @BeforeEach
    void setup() {
        reset(mockEnrollmentFacade, mockRecordFacade,
                mockGuardianDao, mockDownloadHandler);
        setupMockData();
    }

    @AfterEach
    void tearDown() {
        StudentController.clearControllerFactory();
    }

    private void setupMockData() {
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(createEnrollment(1L, "S001", "Kyle",
                "Gatchalian", "Grade 7", "A",
                "Good Standing"));
        enrollments.add(createEnrollment(2L, "S002", "Aaron",
                "Smith", "Grade 8", "B",
                "Good Standing"));
        enrollments.add(createEnrollment(3L, "S003", "Zara",
                "Lopez", "Grade 9", "C",
                "Warning"));

        when(mockEnrollmentFacade.getAllLatestEnrollments())
                .thenReturn(enrollments);

        Guardian guardian = mock(Guardian.class);
        when(guardian.getFirstName()).thenReturn("Parent");
        when(guardian.getLastName()).thenReturn("Guardian");
        when(guardian.getContactNumber())
                .thenReturn("09123456789");

        StudentGuardian sg = mock(StudentGuardian.class);
        when(sg.getGuardian()).thenReturn(guardian);

        when(mockGuardianDao.findGuardianByStudentId(
                anyString()))
                .thenReturn(List.of(sg));

        when(mockRecordFacade.getRecordByStudentId(
                anyString()))
                .thenReturn(new ArrayList<>());
    }

    private Enrollment createEnrollment(long id,
                                        String studentId, String firstName,
                                        String lastName, String level,
                                        String section, String status) {
        Student student = new Student();
        student.setStudentId(studentId);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setMiddleName("M");
        student.setAddress("123 Main St");
        student.setStudentType("Intern");

        DisciplinaryStatus discStatus =
                mock(DisciplinaryStatus.class);
        when(discStatus.getStatus()).thenReturn(status);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(id);
        enrollment.setStudent(student);
        enrollment.setStudentLevel(level);
        enrollment.setSection(section);
        enrollment.setSchoolYear("2024-2025");
        enrollment.setDisciplinaryStatus(discStatus);
        return enrollment;
    }

    private void clearSearch(FxRobot robot) {
        TextField searchField = robot.lookup("#searchField")
                .queryAs(TextField.class);
        robot.interact(() -> searchField.clear());
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testSearchStudent(FxRobot robot) throws InterruptedException {
        robot.clickOn("#searchField");
        Thread.sleep(500);
        robot.write("Kyle Gatchalian");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        TableView<Enrollment> table = robot
                .lookup("#studentTable")
                .queryAs(TableView.class);
        assertEquals(1, table.getItems().size());
        assertEquals("Kyle",
                table.getItems().get(0).getStudent()
                        .getFirstName());
    }

    @Test
    public void testOpenStudentDetails(FxRobot robot) throws InterruptedException  {
        robot.clickOn("Kyle Gatchalian");
        Thread.sleep(500);
        WaitForAsyncUtils.waitForFxEvents();

        TextField nameField = robot
                .lookup("#fullNameTextField")
                .queryAs(TextField.class);
        assertNotNull(nameField);
        assertTrue(nameField.isVisible());
        assertEquals("Kyle M Gatchalian",
                nameField.getText());

        robot.clickOn("Back");
        Thread.sleep(500);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testGradeLevelHistory(FxRobot robot) throws InterruptedException  {
        Record record = new Record();
        Offense offense = new Offense();
        offense.setOffense("Cheating");
        offense.setType("Major");
        record.setOffense(offense);
        record.setDateOfViolation(new Date());

        when(mockRecordFacade.getRecordByStudentId("S001"))
                .thenReturn(List.of(record));

        robot.clickOn("Kyle Gatchalian");
        Thread.sleep(500);
        WaitForAsyncUtils.waitForFxEvents();

        ComboBox<String> gradeBox = robot
                .lookup("#gradeComboBox")
                .queryAs(ComboBox.class);
        robot.interact(() -> {
            gradeBox.getSelectionModel().clearSelection();
            gradeBox.getSelectionModel().select("Grade 7");
        });
        WaitForAsyncUtils.waitForFxEvents();

        TableView<Record> historyTable = robot
                .lookup("#offenseHistoryTable")
                .queryAs(TableView.class);
        assertNotNull(historyTable);
        assertFalse(historyTable.getItems().isEmpty());

        robot.clickOn("Back");
        Thread.sleep(500);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testDownloadStudent(FxRobot robot) throws InterruptedException  {
        robot.clickOn("Kyle Gatchalian");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("Download");
        WaitForAsyncUtils.waitForFxEvents();

        verify(mockDownloadHandler).run();

        robot.clickOn("Back");
        Thread.sleep(500);
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testBackButton(FxRobot robot) throws InterruptedException {
        robot.clickOn("Kyle Gatchalian");
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(robot.lookup("#fullNameTextField")
                .query());

        robot.clickOn("Back");
        Thread.sleep(500);
        WaitForAsyncUtils.waitForFxEvents();

        assertNotNull(robot.lookup("#studentTable")
                .query());
        assertNotNull(robot.lookup("#searchField")
                .query());
    }
}