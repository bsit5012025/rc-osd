package org.rocs.osd.controller.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
        loadDataToTable();
        selectStudentRecord();
    }

    /**
     * Loads student data into the table.
     * Sets cell factories and populates the table.
     */
    public void loadDataToTable() {

        studentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue()
                                .getStudent()
                                .getStudentId()
                )
        );

        // Displays student full name
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

        studentTable.setItems(
                javafx.collections.FXCollections.observableArrayList(
                        enrollmentFacade.getAllLatestEnrollments()
                )
        );
    }
        private void selectStudentRecord() {
            studentTable.setOnMouseClicked(event -> {
                Enrollment enrollment = studentTable.
                        getSelectionModel().getSelectedItem();
                if (enrollment != null) {
                    loadStudentRecord(enrollment);
                }
            });
        }

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
                studentStage.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
