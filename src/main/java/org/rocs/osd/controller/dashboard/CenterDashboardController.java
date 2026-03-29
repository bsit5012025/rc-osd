package org.rocs.osd.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;

import java.time.LocalDate;

public class CenterDashboardController {
    /**
     * Label for total number of violation.
     * */
    @FXML
    private Label totalViolationLabel;
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
}
