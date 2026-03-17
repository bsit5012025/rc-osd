package org.rocs.osd.controller.offense;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.department.Department;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller for managing offenses in the Office of Student Discipline System.
 * This class handles opening the "Add Offense" modal.
 */
public class OffenseController {

    @FXML private TableView<Record> violationsTable;
    @FXML private TableColumn<Record, String> studentIdColumn;
    @FXML private TableColumn<Record, String> studentNameColumn;
    @FXML private TableColumn<Record, String> offenseLevelColumn;
    @FXML private TableColumn<Record, String> offenseTypeColumn;
    @FXML private TableColumn<Record, String> dateColumn;
    @FXML private Label departmentLabel;
    private RecordFacade recordFacade;
    /**
     * Opens the "Add Offense" modal as an undecorated, non-resizable window.
     * @param event the ActionEvent triggered by the user.
     */
    public void onLoadOffenseModal(ActionEvent event){
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/view/offense/addOffenseModal.fxml"));
            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setResizable(false);
            modalStage.setScene(new Scene(root));
            modalStage.show();

        } catch (IOException e) {
            System.err.println("UI Error: Could not find or load AddOffenseModal.fxml. Check the file path and Controller names.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected Error while opening modal: " + e.getMessage());
        }
    }
    @FXML
    public void initialize() {
        recordFacade = new RecordFacadeImpl(new RecordDaoImpl());

        loadDataToTable();
        loadRecordsOfViolation(Department.JHS);
    }

    private void loadDataToTable() {

        studentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEnrollment().getStudent().getStudentId()));

        studentNameColumn.setCellValueFactory(cellData -> {
            var student = cellData.getValue().getEnrollment().getStudent();
            return new SimpleStringProperty(student.getFirstName() + " " + student.getLastName());
        });

        offenseLevelColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getOffense().getType()));

        offenseTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getOffense().getOffense()));

        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateOfViolation().toString()));
    }

    private void loadRecordsOfViolation(Department department) {
        LocalDate now = LocalDate.now();
        String currentSchoolYear;
        int year = now.getYear();
        int month = now.getMonthValue();
        if (month >= 6) {
            currentSchoolYear = year + "-" + (year + 1);
        } else {
            currentSchoolYear = (year - 1) + "-" + year;
        }
        violationsTable.setItems(FXCollections.observableArrayList(recordFacade.getViolationsByDepartment(department, currentSchoolYear)));
    }

    @FXML
    private void onLoadJuniorHS() {
        loadRecordsOfViolation(Department.JHS);
        departmentLabel.setText("Junior HS Violations");
    }

    @FXML
    private void onLoadSeniorHS() {
        loadRecordsOfViolation(Department.SHS);
        departmentLabel.setText("Senior HS Violations");
    }

    @FXML
    private void onLoadCollege() {
        loadRecordsOfViolation(Department.COLLEGE);
        departmentLabel.setText("College Violations");
    }
}
