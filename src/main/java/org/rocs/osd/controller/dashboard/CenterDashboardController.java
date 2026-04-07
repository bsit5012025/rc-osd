package org.rocs.osd.controller.dashboard;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CenterDashboardController {
    /**
     * Label for total number of violation.
     * */
    @FXML
    private Label totalViolationLabel;
    /**
     * Label for month of date.
     * */
    @FXML
    private Label monthLabel;
    /**
     * Label for day of date.
     * */
    @FXML
    private Label dayLabel;
    /**
     * Label for week of date.
     * */
    @FXML
    private Label weekLabel;
    /**
     * Label for time.
     * */
    @FXML
    private Label timeLabel;
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
     * VBox container for frequent offense.
     * */
    @FXML
    private VBox frequentOffenseContainer;
    /**
     * Object for record facade.
     * */
    private RecordFacade recordFacade;

    /**
     * @param pRecordFacade sets the controller for CenterDashboardController.
     */
    public void setRecordFacade(RecordFacade pRecordFacade) {
        this.recordFacade = pRecordFacade;
    }
    /**
     * Initializes the center dashboard controller.
     * */
    @FXML
    public void initialize() {
        if (recordFacade == null) {
            recordFacade = new RecordFacadeImpl(new RecordDaoImpl());
        }
        if (appealFacade == null) {
            appealFacade = new AppealFacadeImpl();
        }
        loadWidgetsOnDashboard();
        loadTime();
        loadDataToTable();
        loadRecentViolations(getCurrentSchoolYear());
        loadFrequentOffense();
    }

    /**
     * Loads the data on fxml components using record facade.
     * */
    public void loadWidgetsOnDashboard() {
        String schoolYear = getCurrentSchoolYear();
        int total = recordFacade.getTotalViolations(schoolYear);
        totalViolationLabel.setText(String.valueOf(total));
        loadPendingAppeals();
        loadOffensesToday();
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

    private void loadTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> loadDateTime())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadDateTime() {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter month = DateTimeFormatter.ofPattern("MMM");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter week = DateTimeFormatter.ofPattern("EEE");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("hh:mm a");

        monthLabel.setText(now.format(month).toUpperCase(Locale.ENGLISH));
        dayLabel.setText(now.format(day));
        weekLabel.setText(now.format(week).toUpperCase(Locale.ENGLISH));
        timeLabel.setText(now.format(time));
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
    /**
     * Label for current number of pendingAppeals.
     * */
    @FXML
    private Label pendingAppealsLabel;

    /**
     * Object for appeal facade.
     * */
    private AppealFacade appealFacade;

    /**
     * @param pAppealFacade sets the controller for CenterDashboardController.
     */
    public void setAppealFacade(AppealFacade pAppealFacade) {
        this.appealFacade = pAppealFacade;
    }
    /**
     * Loads the data on fxml components using appeal facade.
     * */
    private void loadPendingAppeals() {
        List<Appeal> pendingAppeals =
                appealFacade.getAppealsByStatus("PENDING");

        int count = (pendingAppeals != null) ? pendingAppeals.size() : 0;

        pendingAppealsLabel.setText(String.valueOf(count));
    }
    private void loadFrequentOffense() {
        String schoolYear = getCurrentSchoolYear();
        var offenses = recordFacade.getMostFrequentOffense(schoolYear);
        frequentOffenseContainer.getChildren().clear();

        for (Map.Entry<String, Double> entry : offenses.entrySet()) {

            String offenseName = entry.getKey();
            double percentage = entry.getValue();
            double progress = percentage / 100.0;

            HBox row = new HBox();
            row.getStyleClass().add("offenseRow");
            Label nameLabel = new Label(offenseName);
            nameLabel.getStyleClass().add("offenseName");
            ProgressBar progressBar = new ProgressBar(progress);
            progressBar.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(progressBar, javafx.scene.layout.Priority.ALWAYS);

            row.getChildren().addAll(nameLabel, progressBar);
            frequentOffenseContainer.getChildren().add(row);
        }
    }

    /**
     * Object for offense label.
     * */
    @FXML
    private Label offensesTodayLabel;

    /**
     * Loads the data on fxml components using record facade.
     * */
    private void loadOffensesToday() {
        int todayCount = recordFacade.getTodayViolations();
        offensesTodayLabel.setText(String.valueOf(todayCount));
    }
}
