package org.rocs.osd.controller.dashboard;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        loadTime();
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

        monthLabel.setText(now.format(month).toUpperCase());
        dayLabel.setText(now.format(day));
        weekLabel.setText(now.format(week).toUpperCase());
        timeLabel.setText(now.format(time));
    }
}
