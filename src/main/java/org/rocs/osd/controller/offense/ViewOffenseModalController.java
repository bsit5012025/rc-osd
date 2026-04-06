package org.rocs.osd.controller.offense;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.rocs.osd.data.dao.disciplinary.action.DisciplinaryActionDao;
import org.rocs.osd.data.dao.disciplinary.action.impl.DisciplinaryActionImpl;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.data.dao.enrollment.impl.EnrollmentDaoImpl;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.data.dao.offense.impl.OffenseDaoImpl;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.sql.Date;

/**
 * Controller for viewing a selected offense record.
 * Allows user to either edit or resolve the record.
 */
public class ViewOffenseModalController {

    /**
     * TextField for student ID.
     */
    @FXML
    private TextField studentIdField;

    /**
     * TextField for student name.
     */
    @FXML
    private TextField studentNameField;

    /**
     * DatePicker for violation date.
     */
    @FXML
    private DatePicker datePicker;

    /**
     * ComboBox for offense type.
     */
    @FXML
    private ComboBox<String> offenseTypeField;

    /**
     * Text field for displaying offense level.
     */
    @FXML
    private TextField offenseLevelField;

    /**
     * TextField for action.
     */
    @FXML
    private TextField actionField;

    /**
     * TextArea for remarks.
     */
    @FXML
    private TextArea remarksField;

    /**
     * Facade for record operations.
     */
    private RecordFacade recordFacade;

    /**
     * DAO for offense operations.
     */
    private OffenseDao offenseDao;

    /**
     * DAO for enrollment operations.
     */
    private EnrollmentDao enrollmentDao;

    /**
     * DAO for disciplinary actions.
     */
    private DisciplinaryActionDao disciplinaryActionDao;

    /**
     * Record being viewed.
     */
    private Record record;

    /**
     * Initializes controller.
     */
    @FXML
    public void initialize() {
        offenseDao = new OffenseDaoImpl();
        enrollmentDao = new EnrollmentDaoImpl();
        RecordDao dao = new RecordDaoImpl();
        disciplinaryActionDao = new DisciplinaryActionImpl();
        recordFacade = new RecordFacadeImpl(dao);
    }

    /**
     * Sets record data and loads it to UI.
     *
     * @param pRecord the selected record
     */
    public void setRecordData(Record pRecord) {
        this.record = pRecord;
        loadRecordData();
    }

    /**
     * Loads record data into UI fields.
     */
    private void loadRecordData() {

        studentIdField.setText(
                record.getEnrollment().getStudent().getStudentId()
        );

        studentNameField.setText(
                record.getEnrollment().getStudent().getFirstName()
                        + " "
                        + record.getEnrollment().getStudent().getLastName()
        );

        datePicker.setValue(
                new Date(record.getDateOfViolation().getTime()).toLocalDate()
        );

        offenseTypeField.setValue(
                record.getOffense().getOffense()
        );

        offenseLevelField.setText(
                record.getOffense().getType()
        );

        actionField.setText(
                record.getAction().getActionName()
        );

        remarksField.setText(record.getRemarks());
    }

    /**
     * Opens edit modal for this record.
     *
     * @param event button click event
     */
    @FXML
    void onEdit(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/offense/editOffenseModal.fxml"));

            Parent root = loader.load();

            EditOffenseModalController controller =
                    loader.getController();

            Stage viewOffenseModalStage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            controller.setRecordData(record, viewOffenseModalStage);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Resolves the selected record.
     *
     * @param event button click event
     */
    @FXML
    void onResolve(ActionEvent event) {

        try {
            String studentId = studentIdField.getText();
            String studentName = studentNameField.getText();
            String offenseName = offenseTypeField.getValue();
            String offenseType = offenseLevelField.getText();
            String remarks = remarksField.getText();

            if (studentId == null
                    || studentId.isEmpty()
                    || studentName == null
                    || studentName.isEmpty()
                    || offenseName == null
                    || offenseType == null
                    || datePicker.getValue() == null) {
                System.out.println("Fill out all required fields!");
                return;
            }

            if (record == null
                    || record.getEnrollment() == null
                    || record.getAction() == null
                    || record.getEnrollment().getStudent() == null) {
                System.out.println("Record, Enrollment, "
                        + "Action or student are missing or null!");
                return;
            }

            Date dateOfViolation = Date.valueOf(datePicker.getValue());

            long enrollmentID = enrollmentDao.
                    findEnrollmentIdByStudentId(studentId);
            record.getEnrollment().setEnrollmentId(enrollmentID);

            long actionId = disciplinaryActionDao.
                    findActionIdByName(record.getAction().getActionName());
            record.getAction().setActionId(actionId);

            Offense offenseObj = offenseDao.findByName(offenseName);
            record.setOffense(offenseObj);

            record.setRemarks(remarks);
            record.setDateOfViolation(dateOfViolation);
            record.setStatus(RecordStatus.PENDING);

            boolean status = recordFacade.resolveRecord(record);

            if (status) {
                System.out.println("Record Resolved");

                Stage stage = (Stage) ((Node) event.getSource())
                        .getScene().getWindow();
                stage.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
