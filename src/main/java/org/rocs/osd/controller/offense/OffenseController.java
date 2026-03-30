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
 * Controller for managing offenses in the
 * Office of Student Discipline System.
 * This class handles opening the "Add Offense" modal.
 */
public class OffenseController {
    /**
     * Table displaying list of violations.
     */
    @FXML private TableView<Record> violationsTable;
    /**
     * Column for student ID.
     */
    @FXML private TableColumn<Record, String> studentIdColumn;
    /**
     * Column for student name.
     */
    @FXML private TableColumn<Record, String> studentNameColumn;
    /**
     * Column for offense level.
     */
    @FXML private TableColumn<Record, String> offenseLevelColumn;
    /**
     * Column for offense type.
     */
    @FXML private TableColumn<Record, String> offenseTypeColumn;
    /**
     * Column for violation date.
     */
    @FXML private TableColumn<Record, String> dateColumn;
    /**
     * Label displaying current department.
     */
    @FXML private Label departmentLabel;
    /**
     * Facade for accessing record data.
     */
    private RecordFacade recordFacade;
    /**
     * Opens the "Add Offense" modal as an
     * undecorated, non-resizable window.
     * @param event the ActionEvent triggered by the user.
     */
    public void onLoadOffenseModal(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().
            getResource("/view/offense/addOffenseModal.fxml"));
            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setResizable(false);
            modalStage.setScene(new Scene(root));
            modalStage.setOnHidden(e -> refreshRecord());
            modalStage.showAndWait();
            refreshRecord();

        } catch (IOException e) {
            System.err.println("UI Error: Could not find or load "
                    + "AddOffenseModal.fxml. "
                    + "Check the file path and Controller names.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected Error while opening modal: "
            + e.getMessage());
        }
    }
    /**
     *  Opens the View Offense modal with selected record.
     *
     * @param record the selected record to edit
     */
    public void onLoadViewOffenseModal(Record record) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/offense/viewStudentOffenseModal.fxml"));
            Parent root = loader.load();

            ViewOffenseModalController controller = loader.getController();
            controller.setRecordData(record);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            modalStage.setOnHidden(e -> refreshRecord());

            modalStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Initializes controller and loads initial data.
     */
    @FXML
    public void initialize() {
        recordFacade = new RecordFacadeImpl(new RecordDaoImpl());

        loadDataToTable();
        selectStudentRecord();
        loadRecordsOfViolation(Department.JHS);
    }
    /**
     * Sets up table column mappings.
     */
    private void loadDataToTable() {
        studentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue()
                .getEnrollment()
                .getStudent()
                .getStudentId()));

        studentNameColumn.setCellValueFactory(cellData -> {
            var student = cellData.getValue()
                    .getEnrollment()
                    .getStudent();
            return new SimpleStringProperty(
                    student.getFirstName()
                            + " "
                            + student.getLastName());
        });

        offenseLevelColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.
                getValue().getOffense().getType()));

        offenseTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.
                getValue().getOffense().getOffense()));

        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().
                getDateOfViolation().toString()));
    }
    /**
     * Loads violations filtered by department and school year.
     *
     * @param department the selected department.
     */
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
        violationsTable.setItems(FXCollections.observableArrayList(recordFacade.
        getViolationsByDepartment(department, currentSchoolYear)));
    }
    /**
     * Handles row click to open edit modal.
     */
    private void selectStudentRecord() {
        violationsTable.setOnMouseClicked(event -> {
                Record record = violationsTable.
                getSelectionModel().getSelectedItem();
                if (record != null) {
                    onLoadViewOffenseModal(record);
                }
        });
    }
    /**
     * Handles row click to open edit modal.
     */
    @FXML
    private void onLoadJuniorHS() {
        loadRecordsOfViolation(Department.JHS);
        departmentLabel.setText("Junior HS Violations");
        refreshRecord();
    }
    /**
     * Loads Senior High School violations.
     */
    @FXML
    private void onLoadSeniorHS() {
        loadRecordsOfViolation(Department.SHS);
        departmentLabel.setText("Senior HS Violations");
    }
    /**
     * Loads College violations.
     */
    @FXML
    private void onLoadCollege() {
        loadRecordsOfViolation(Department.COLLEGE);
        departmentLabel.setText("College Violations");
    }
    /**
     * Refreshes the violation table.
     */
    public void refreshRecord() {
        loadRecordsOfViolation(Department.JHS);
    }
}
