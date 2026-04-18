package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rocs.osd.data.dao.disciplinary.action.DisciplinaryActionDao;
import org.rocs.osd.data.dao.disciplinary.action.impl.DisciplinaryActionImpl;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.data.dao.enrollment.impl.EnrollmentDaoImpl;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.data.dao.offense.impl.OffenseDaoImpl;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.data.dao.student.StudendDao;
import org.rocs.osd.data.dao.student.impl.StudentDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.RecordStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
/**
 * Controller for editing an existing offense record
 * in the Office of Student Discipline System.
 * Handles loading, updating, and validating record data.
 */
public class EditOffenseModalController {
    /**
     * Logger instance of this class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(EditOffenseModalController.class);
    /**
     * ComboBox for selecting offense type.
     */
    @FXML
    private ComboBox<String> offenseTypeComboBox;
    /**
     * Text field for displaying offense level.
     */
    @FXML
    private TextField levelOfOffense;
    /**
     * Dropdown for selecting disciplinary action.
     */
    @FXML
    private ComboBox<String> actionComboBox;
    /**
     * TextField for student ID input.
     */
    @FXML
    private TextField studentIdTextField;
    /**
     * TextField for student name display.
     */
    @FXML
    private TextField studentNameTextField;
    /**
     * DatePicker for selecting violation date.
     */
    @FXML
    private DatePicker datePicker;
    /**
     * TextField for remarks input.
     */
    @FXML
    private TextArea remarksTextArea;
    /**
     * DAO for student operations.
     */
    private StudendDao studentDao;
    /**
     * DAO for offense operations.
     */
    private OffenseDao offenseDao;
    /**
     * DAO for enrollment operations.
     */
    private EnrollmentDao enrollmentDao;
    /**
     * Facade for record operations.
     */
    private RecordFacade recordFacade;
    /**
     * DAO for disciplinary actions.
     */
    private DisciplinaryActionDao disciplinaryActionDao;
    /**
     * Record object being edited.
     */
    private Record record;
    /**
     * Reference to the parent (viewOffenseModal) stage.
     */
    private Stage viewOffenseModalStage;

    /**
     * Initializes the controller.
     * Sets up dependencies and loads initial data.
     */
    public void initialize() {
        offenseDao = new OffenseDaoImpl();
        studentDao = new StudentDaoImpl();
        enrollmentDao = new EnrollmentDaoImpl();
        RecordDao dao = new RecordDaoImpl();
        disciplinaryActionDao = new DisciplinaryActionImpl();
        recordFacade = new RecordFacadeImpl(dao);

        loadComboBoxData();
        autoSelectLevelOfOffense();
        studentIdTextField.setOnAction(e -> autoDisplayStudentName());
    }

    /**
     * Sets the prev stage and record data to be edited.
     *
     * @param pRecord the record to load into the form.
     * @param pStage  the parent stage (ViewOffenseModal)
     *                that will be closed after submitting edits.
     */
    public void setRecordData(Record pRecord, Stage pStage) {
        this.record = pRecord;
        this.viewOffenseModalStage = pStage;
        loadStudentRecordInfo();
    }

    /**
     * Loads student and record details into the UI fields.
     */
    private void loadStudentRecordInfo() {
        studentIdTextField.setText(
                record.getEnrollment().getStudent().getStudentId()
        );

        studentNameTextField.setText(
                record.getEnrollment().getStudent().getFirstName()
                        + " "
                        + record.getEnrollment().getStudent().getMiddleName()
                        + " "
                        + record.getEnrollment().getStudent().getLastName()
        );

        datePicker.setValue(LocalDate.parse(
                String.valueOf(record.getDateOfViolation())));
        offenseTypeComboBox.setValue(
                record.getOffense().getOffense());
        levelOfOffense.setText(
                record.getOffense().getType());
        remarksTextArea.setWrapText(true);
        remarksTextArea.setText(
                record.getRemarks());
        actionComboBox.setValue(
                record.getAction().getActionName());
    }

    /**
     * Loads offense names into the ComboBox.
     */
    private void loadComboBoxData() {
        try {
            ObservableList<String> offenseList = FXCollections.
                    observableArrayList(offenseDao.findAllOffenseName());
            ObservableList<String> actionList = FXCollections.
                    observableArrayList(disciplinaryActionDao.findAllAction());
            offenseTypeComboBox.setItems(offenseList);
            actionComboBox.setItems(actionList);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Could not fetch offense names "
                        + "from the database", e);
            }
        }
    }

    /**
     * Automatically sets offense level when offense type is selected.
     */
    private void autoSelectLevelOfOffense() {

        offenseTypeComboBox.setOnAction(event -> {

            String selected = offenseTypeComboBox.getValue();

            if (selected != null) {

                Offense offense = offenseDao.findByName(selected);

                if (offense != null) {
                    levelOfOffense.setText(offense.getType());
                }
            }
        });
    }

    /**
     * Displays student name based on entered student ID.
     */
    @FXML
    private void autoDisplayStudentName() {

        String studentId = studentIdTextField.getText();

        if (studentId.isEmpty()) {
            return;
        }

        Student student = studentDao.findStudentWithRecordById(studentId);

        if (student.getStudentId() != null) {
            String fullName = student.getFirstName()
                    + " "
                    + student.getMiddleName()
                    + " "
                    + student.getLastName();

            studentNameTextField.setText(fullName);
        } else {
            studentNameTextField.clear();
            LOGGER.info("STUDENT NOT FOUND!");
        }
    }

    /**
     * Closes the modal when cancel button is clicked.
     *
     * @param event action event from cancel button
     */
    @FXML
    void onCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles submission of updated offense record.
     * Validates inputs and updates the record in database.
     *
     * @param event action event from submit button.
     */
    @FXML
    void onSubmit(ActionEvent event) {
        try {
            String studentId = studentIdTextField.getText();
            String studentName = studentNameTextField.getText();
            String offenseName = offenseTypeComboBox.getValue();
            String offenseType = levelOfOffense.getText();
            String actionName = actionComboBox.getValue();

            String remarks = remarksTextArea.getText();

            if (studentId == null
                    || studentId.isEmpty()
                    || studentName == null
                    || studentName.isEmpty()
                    || offenseName == null
                    || offenseType == null
                    || datePicker.getValue() == null) {
                LOGGER.info("Fill out all required fields!");
                return;
            }

            if (record == null
                    || record.getEnrollment() == null
                    || record.getAction() == null
                    || record.getEnrollment().getStudent() == null) {
                LOGGER.info("Record, Enrollment, "
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

            long actionID = disciplinaryActionDao.
                    findActionIdByName(actionName);
            record.getAction().setActionId(actionID);

            Offense offenseObj = offenseDao.findByName(offenseName);
            record.setOffense(offenseObj);

            record.setRemarks(remarks);
            record.setDateOfViolation(dateOfViolation);
            record.setStatus(RecordStatus.PENDING);

            boolean status = recordFacade.updateStudentRecord(
                    record.getRecordId(),
                    record.getEnrollment(),
                    record.getEmployee(),
                    record.getOffense(),
                    record.getDateOfViolation(),
                    record.getAction(),
                    record.getRemarks(),
                    record.getStatus()
            );

            if (status) {
                LOGGER.info("Violation Updated!");

                Stage stage = (Stage) ((Node) event.getSource())
                        .getScene().getWindow();
                stage.close();
                viewOffenseModalStage.close();
            }

        } catch (NullPointerException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Data mapping error", e);
            }
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Unexpected error updating Record ID: "
                                + "{}. Error: {}",
                        record != null ? record.getRecordId() : "Unknown",
                        e.getMessage(), e);
            }
        }
    }
}

