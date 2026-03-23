package org.rocs.osd.controller.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
}
