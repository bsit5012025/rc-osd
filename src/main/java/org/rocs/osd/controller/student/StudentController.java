package org.rocs.osd.controller.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rocs.osd.data.dao.enrollment.impl.EnrollmentDaoImpl;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.facade.enrollment.impl.EnrollmentFacadeImpl;
import org.rocs.osd.model.enrollment.Enrollment;

/**
 * Controller for managing student records in the table view.
 * Handles loading student enrollment data and displaying it.
 */
public class StudentController {

    /** Table view displaying student enrollments. */
    @FXML
    private TableView<Enrollment> studentTable;

    /** Column for student ID. */
    @FXML
    private TableColumn<Enrollment, String> studentIdColumn;

    /** Column for student full name. */
    @FXML
    private TableColumn<Enrollment, String> studentNameColumn;

    /** Column for grade level or course. */
    @FXML
    private TableColumn<Enrollment, String> gradeCourseColumn;

    /** Column for section. */
    @FXML
    private TableColumn<Enrollment, String> sectionColumn;

    /** TextField for searching for Students. **/
    @FXML
    private TextField searchField;

    /** Facade used to retrieve enrollment data. */
    private EnrollmentFacade enrollmentFacade;

    /**
     * Initializes the controller.
     * Instantiates the facade and loads table data.
     */
    @FXML
    public void initialize() {
        enrollmentFacade = new EnrollmentFacadeImpl(
                new EnrollmentDaoImpl()
        );

        loadDataToColumns();
        searchField.setOnAction(e -> searchStudents());
        loadDataToTable();
        selectStudentRecord();
    }

    /**
     * Handles the student search button action.
     */
    @FXML
    public void searchStudentInfo() {
        searchStudents();
    }

    /**
     * Searches for students info on the entered search criteria
     * and updates the table with the latest students.
     */
    private void searchStudents() {
        studentTable.setItems(
                javafx.collections.FXCollections.observableArrayList(
                        enrollmentFacade.getLatestEnrollmentByStudentInfo(
                                searchField.getText()
                        )
                )
        );
    }

    /**
     * Loads student data into the table.
     */
    private void loadDataToTable() {
        studentTable.setItems(
                javafx.collections.FXCollections.observableArrayList(
                        enrollmentFacade.getAllLatestEnrollments()
                )
        );
    }

    /**
     * Sets cell factories and populates the table.
     */
    private void loadDataToColumns() {

        studentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue()
                                .getStudent()
                                .getStudentId()
                )
        );

        studentNameColumn.setCellValueFactory(cellData -> {
            var student = cellData.getValue().getStudent();
            return new SimpleStringProperty(
                    student.getFirstName()
                            + " "
                            + student.getLastName()
            );
        });

        gradeCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getStudentLevel()
                )
        );

        sectionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getSection()
                )
        );
    }

    /**
     * Allows clicking only on rows of the table view,
     * Ignores clicks on empty areas and column titles.
     */
    private void selectStudentRecord() {
        studentTable.setRowFactory(wholeRow -> {
            TableRow<Enrollment> row = new TableRow<>();
            row.setOnMouseClicked(evenClickOnRow -> {
                if (!row.isEmpty()) {
                    Enrollment enrollment = studentTable.
                            getSelectionModel().getSelectedItem();
                    loadStudentRecord(enrollment);
                }
            });
            return row;
        });
    }

    /**
     * Opens the student Record with selected info
     * of the student.
     *
     * @param enrollment student info to be displayed.
     */
    private void loadStudentRecord(Enrollment enrollment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/student/studentRecord.fxml"));
            Parent root = loader.load();

            StudentRecordController studentRecordController
                    = loader.getController();
            studentRecordController.setStudentData(enrollment);

            Stage studentStage = new Stage();

            studentStage.initModality(Modality.APPLICATION_MODAL);
            studentStage.initStyle(StageStyle.UNDECORATED);
            studentStage.setResizable(false);
            studentStage.setScene(new Scene(root));
            studentStage.centerOnScreen();
            studentStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
