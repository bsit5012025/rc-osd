package org.rocs.osd.controller.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;

import java.time.LocalDate;
import java.util.List;

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
        appealFacade = new AppealFacadeImpl();
        loadWidgetsOnDashboard();
    }
    /**
     * Loads the data on fxml components using record facade.
     * */
    public void loadWidgetsOnDashboard() {
        String schoolYear = getCurrentSchoolYear();
        int total = recordFacade.getTotalViolations(schoolYear);
        totalViolationLabel.setText(String.valueOf(total));
        loadPendingAppeals();
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
     * Label for current number of pendingAppeals.
     * */
    @FXML
    private Label pendingAppealsLabel;

    /**
     * Object for appeal facade.
     * */
    private AppealFacade appealFacade;

    /**
     * Loads the data on fxml components using appeal facade.
     * */
    private void loadPendingAppeals() {
        List<Appeal> pendingAppeals =
                appealFacade.getAppealsByStatus("PENDING");

        int count = (pendingAppeals != null) ? pendingAppeals.size() : 0;

        pendingAppealsLabel.setText(String.valueOf(count));
    }
}
