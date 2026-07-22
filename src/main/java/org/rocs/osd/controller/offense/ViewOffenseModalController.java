package org.rocs.osd.controller.offense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rocs.osd.controller.dialog.ConfirmationDialogController;
import org.rocs.osd.data.dao.disciplinary.action.DisciplinaryActionDao;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.model.disciplinary.action.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controller for viewing an offense record
 * in the Office of Student Discipline System.
 * Handles displaying record details and initiating edit/resolve actions.
 */
public class ViewOffenseModalController {

    /** Field for student ID display. */
    @FXML
    private TextField studentIdField;
    /** Field for student name display. */
    @FXML
    private TextField studentNameField;
    /** DatePicker for violation date display. */
    @FXML
    private DatePicker datePicker;
    /** Field for offense type display. */
    @FXML
    private TextField offenseTypeField;
    /** Field for offense level display. */
    @FXML
    private TextField offenseLevelField;
    /** Field for action display. */
    @FXML
    private TextField actionField;
    /** Area for remarks display. */
    @FXML
    private TextArea remarksField;
    /** Button to edit the record. */
    @FXML
    private Button editButton;
    /** Button to resolve the record. */
    @FXML
    private Button resolveButton;

    /** Facade for record operations. */
    private RecordFacade recordFacade;
    /** DAO for offense operations. */
    private OffenseDao offenseDao;
    /** DAO for enrollment operations. */
    private EnrollmentDao enrollmentDao;
    /** DAO for disciplinary actions. */
    private DisciplinaryActionDao disciplinaryActionDao;

    /** The record being viewed. */
    private Record record;

    /** Static mock for edit offense modal opener (for testing). */
    private static Consumer<Record> mockEditOffenseModal;
    /** Static mock for confirm resolve dialog (for testing). */
    private static Runnable mockConfirmResolve;

    /**
     * Sets mock edit offense modal opener for testing.
     *
     * @param pMock the mock consumer
     */
    public static void setMockEditOffenseModal(Consumer<Record> pMock) {
        mockEditOffenseModal = pMock;
    }

    /**
     * Sets mock confirm resolve dialog for testing.
     *
     * @param pMock the mock runnable
     */
    public static void setMockConfirmResolve(Runnable pMock) {
        mockConfirmResolve = pMock;
    }

    /**
     * Clears static mocks.
     */
    public static void clearMocks() {
        mockEditOffenseModal = null;
        mockConfirmResolve = null;
    }

    /**
     * Sets the record facade for dependency injection.
     *
     * @param pFacade the facade to use
     */
    public void setRecordFacade(RecordFacade pFacade) {
        this.recordFacade = pFacade;
    }

    /**
     * Sets the offense DAO for dependency injection.
     *
     * @param pDao the DAO to use
     */
    public void setOffenseDao(OffenseDao pDao) {
        this.offenseDao = pDao;
    }

    /**
     * Sets the enrollment DAO for dependency injection.
     *
     * @param pDao the DAO to use
     */
    public void setEnrollmentDao(EnrollmentDao pDao) {
        this.enrollmentDao = pDao;
    }

    /**
     * Sets the disciplinary action DAO for dependency injection.
     *
     * @param pDao the DAO to use
     */
    public void setDisciplinaryActionDao(DisciplinaryActionDao pDao) {
        this.disciplinaryActionDao = pDao;
    }

    /**
     * Sets the record data to display.
     *
     * @param pRecord the record to display
     */
    public void setRecordData(Record pRecord) {
        this.record = pRecord;
        loadRecordInfo();
    }
    /**
     * Loads record information into the UI fields using DAOs for fresh data.
     */
    private void loadRecordInfo() {
        if (record == null) {
            return;
        }

        Enrollment enrollment = record.getEnrollment();
        if (enrollment != null && enrollment.getStudent() != null
                && enrollmentDao != null) {
            String studentId = enrollment.getStudent().getStudentId();
            List<Enrollment> freshEnrollments =
                    enrollmentDao.findEnrollmentsByStudentId(studentId);
            if (freshEnrollments != null && !freshEnrollments.isEmpty()) {
                enrollment = freshEnrollments.get(0);
            }
        }

        Offense offense = record.getOffense();
        if (offense != null && offenseDao != null) {

            Offense freshOffense = offenseDao.findOffenseById(
                    String.valueOf(offense.getOffenseId()));
            if (freshOffense != null && freshOffense.getOffense() != null
                    && !freshOffense.getOffense().isEmpty()) {
                offense = freshOffense;
            }
        }

        DisciplinaryAction action = record.getAction();
        if (action != null && disciplinaryActionDao != null) {
            String freshActionName = disciplinaryActionDao.findActionById(
                    action.getActionId());
            if (freshActionName != null) {
                action.setActionName(freshActionName);
            }
        }

        if (enrollment != null && enrollment.getStudent() != null) {
            studentIdField.setText(enrollment.getStudent().getStudentId());

            String firstName = enrollment.getStudent().getFirstName();
            String middleName = enrollment.getStudent().getMiddleName();
            String lastName = enrollment.getStudent().getLastName();

            StringBuilder nameBuilder = new StringBuilder();
            if (firstName != null) {
                nameBuilder.append(firstName).append(" ");
            }
            if (middleName != null) {
                nameBuilder.append(middleName).append(" ");
            }
            if (lastName != null) {
                nameBuilder.append(lastName);
            }

            studentNameField.setText(nameBuilder.toString().trim());
        }

        if (record.getDateOfViolation() != null) {
            datePicker.setValue(LocalDate.parse(
                    String.valueOf(record.getDateOfViolation())));
        }

        if (offense != null) {
            offenseTypeField.setText(offense.getOffense());
            offenseLevelField.setText(offense.getType());
        }

        if (action != null) {
            actionField.setText(action.getActionName());
        }

        remarksField.setText(record.getRemarks());
        remarksField.setWrapText(true);
    }

    /**
     * Handles edit button click.
     *
     * @param event the action event
     */
    @FXML
    void onEdit(ActionEvent event) {
        if (mockEditOffenseModal != null) {
            mockEditOffenseModal.accept(record);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/offense/editOffenseModal.fxml"));
            Parent root = loader.load();

            EditOffenseModalController controller = loader.getController();
            controller.setRecordData(record,
                    (Stage) editButton.getScene().getWindow());

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));
            modalStage.show();

            ((Stage) editButton.getScene().getWindow()).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles resolve button click.
     *
     * @param event the action event
     */
    @FXML
    void onResolve(ActionEvent event) {
        if (mockConfirmResolve != null) {
            mockConfirmResolve.run();
            recordFacade.resolveRecord(record);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dialogs/confirmation.fxml"));
            Parent root = loader.load();
            ConfirmationDialogController controller = loader.getController();
            controller.setMessage("Are you sure you want to",
                    "resolve this violation?");
            controller.setButtonLabels("Yes", "No");
            controller.setOnConfirm(() -> {
                recordFacade.resolveRecord(record);
                ((Stage) resolveButton.getScene().getWindow()).close();
            });

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
