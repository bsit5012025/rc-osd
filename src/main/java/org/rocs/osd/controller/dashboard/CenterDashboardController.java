package org.rocs.osd.controller.dashboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.record.Record;

import java.time.LocalDate;
import java.util.List;

public class CenterDashboardController {
    /**
     * Label for total number of violation.
     * */
    @FXML
    private Label totalViolationLabel;
    /**
     * Table view for recent violations.
     * */
    @FXML
    private TableView<Record> recentViolations;
    /**
     * Table column for student ID.
     * */
    @FXML
    private TableColumn<Record, String> studentIdColumn;
    /**
     * Table column for student name.
     * */
    @FXML
    private TableColumn<Record, String> studentNameColumn;
    /**
     * Table column for level of offense.
     * */
    @FXML
    private TableColumn<Record, String> levelOfOffenseColumn;
    /**
     * Table column for offense type.
     * */
    @FXML
    private TableColumn<Record, String> offenseTypeColumn;
    /**
     * Table column for date.
     * */
    @FXML
    private TableColumn<Record, String> dateColumn;
    /**
     * Object for record facade.
     * */
    private RecordFacade recordFacade;
    /**
     * Initializes the center dashboard controller.
     * */
    @FXML
    public void initialize() {
        recordFacade = new RecordFacadeImpl(new RecordDaoImpl());
        loadWidgetsOnDashboard();
        loadDataToTable();
        loadRecentViolations(getCurrentSchoolYear());
    }
    /**
     * Loads the data on fxml components using record facade.
     * */
    public void loadWidgetsOnDashboard() {
        String schoolYear = getCurrentSchoolYear();
        int total = recordFacade.getTotalViolations(schoolYear);
        totalViolationLabel.setText(String.valueOf(total));
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
    /**
     * Call record facade to dispaly recent violations on table view.
     * @param schoolYear the school year to filter records
     */
    public void loadRecentViolations(String schoolYear) {
        List<Record> records = recordFacade.getRecentViolations(schoolYear, 10);

        recentViolations.setItems(FXCollections.observableArrayList(records));
    }
    /**
     * Fetch data to table columns.
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

        levelOfOffenseColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getOffense().getType()));

        offenseTypeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getOffense().getOffense()));

        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().
                        getDateOfViolation().toString()));
    }
}
