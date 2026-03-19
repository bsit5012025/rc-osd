package org.rocs.osd.controller.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rocs.osd.data.dao.enrollment.impl.EnrollmentDaoImpl;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.facade.enrollment.impl.EnrollmentFacadeImpl;
import org.rocs.osd.model.enrollment.Enrollment;

public class StudentController {
    @FXML
    private TableView<Enrollment> studentTable;
    @FXML
    private TableColumn<Enrollment, String> studentIdColumn;
    @FXML
    private TableColumn<Enrollment, String> studentNameColumn;
    @FXML
    private TableColumn<Enrollment, String> gradeCourseColumn;
    @FXML
    private TableColumn<Enrollment, String> sectionColumn;

    private EnrollmentFacade enrollmentFacade;

    @FXML
    public void initialize() {
        enrollmentFacade = new EnrollmentFacadeImpl(new EnrollmentDaoImpl());

        loadDataToTable();
    }
    public void loadDataToTable(){
        studentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudent().getStudentId()));

        studentNameColumn.setCellValueFactory(cellData -> {
            var student = cellData.getValue().getStudent();
            return new SimpleStringProperty(student.getFirstName() + " " + student.getLastName());});

        gradeCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStudentLevel()));

        sectionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSection()));

        studentTable.setItems(
                javafx.collections.FXCollections.observableArrayList(
                        enrollmentFacade.getAllLatestEnrollments()
                )
        );
    }
}
