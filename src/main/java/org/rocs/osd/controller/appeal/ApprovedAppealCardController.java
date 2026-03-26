package org.rocs.osd.controller.appeal;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

/**
 * Controller for handling approved appeal card behavior.
 */
public class ApprovedAppealCardController {

    /**
     * The expandable section of the card.
     */
    @FXML
    private VBox expandedSection;

    /**
     * The arrow icon used to indicate expansion state.
     */
    @FXML
    private ImageView arrowIcon;

    /**
     * Tracks whether the card is expanded or collapsed.
     */
    private boolean isExpanded = false;

    /**
     * Toggles the expansion state of the card.
     */
    @FXML
    private void toggleExpansion() {
        isExpanded = !isExpanded;

        if (expandedSection != null) {
            expandedSection.setVisible(isExpanded);
            expandedSection.setManaged(isExpanded);
        }

        String imgPath = isExpanded
                ? "/assets/downButton.png"
                : "/assets/rightButton.png";

        try {
            if (arrowIcon != null) {
                arrowIcon.setImage(
                        new Image(getClass().getResourceAsStream(imgPath))
                );
            }
        } catch (Exception e) {
            if (arrowIcon != null) {
                arrowIcon.setRotate(isExpanded ? 90 : 0);
            }
        }
    }

    /**
     * Student ID label.
     */
    @FXML
    private Label studentIdLabel;

    /**
     * Student name label.
     */
    @FXML
    private Label studentNameLabel;

    /**
     * Offense label.
     */
    @FXML
    private Label offenseLabel;

    /**
     * Reason label.
     */
    @FXML
    private Label reasonLabel;

    /**
     * Remarks display area.
     */
    @FXML
    private TextArea commentArea;

    /**
     * Sets appeal data into UI components.
     * @param appeal set appeal data.
     */
    public void setAppeal(Appeal appeal) {
        if (appeal == null) {
            return;
        }

            Enrollment e = appeal.getEnrollment();
            Record r = appeal.getRecord();
            Student s = e.getStudent();

            if (studentIdLabel != null) {
                studentIdLabel.setText(s.getStudentId());
            }
            if (studentNameLabel != null) {
                studentNameLabel.setText(
                        s.getFirstName() + " " + s.getLastName()
                );
            }
            if (offenseLabel != null) {
                offenseLabel.setText(r.getRemarks());
            }
            if (reasonLabel != null) {
                reasonLabel.setText(appeal.getMessage());
            }
            if (commentArea != null) {
                commentArea.setText(appeal.getRemarks());
            }
        }
    }
