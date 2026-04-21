package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rocs.osd.controller.dialog.ConfirmationDialogController;
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

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Controller for editing an existing offense record
 * in the Office of Student Discipline System.
 * Handles loading, updating, and validating record data.
 */
public class EditOffenseModalController {
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
     * that will be closed after submitting edits.
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
            System.err.println("Database Error: Could not fetch "
                    + "offense names from the database.");
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
            System.out.println("STUDENT NOT FOUND!");
        }
    }

    /**
     * Closes the modal when cancel button is clicked.
     *
     * @param event action event from cancel button
     */
    @FXML
    void onCancel(ActionEvent event) {
        showConfirmation(
                "Are you sure you want ",
                "to cancel?",
                () -> {
                    Stage stage = (Stage) (
                            (Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
        );
    }

    /**
     * Handles submission of updated offense record.
     * Validates inputs and updates the record in database.
     *
     * @param event action event from submit button.
     */
    @FXML
    void onSubmit(ActionEvent event) {
        if (studentIdTextField.getText().isEmpty()
                || studentNameTextField.getText().isEmpty()
                || offenseTypeComboBox.getValue() == null
                || actionComboBox.getValue() == null
                || datePicker.getValue() == null) {
            System.out.println("Fill out all required fields!");
            return;
        }

        showConfirmation(
                "Do you want to ",
                "save changes?",
                () -> saveUpdatedRecord(event)
        );
    }

    /**
     * Logic to update the student record in the database.
     * Executes after confirmation.
     *
     * @param event the event from the submit button
     */
    private void saveUpdatedRecord(ActionEvent event) {
        try {
            if (record == null
                    || record.getEnrollment() == null
                    || record.getAction() == null
                    || record.getEnrollment().getStudent() == null) {
                System.out.println("Record parts are missing!");
                return;
            }

            Date dateOfViolation = Date.valueOf(datePicker.getValue());
            long enrollmentID = enrollmentDao.findEnrollmentIdByStudentId(
                    studentIdTextField.getText());
            long actionID = disciplinaryActionDao.findActionIdByName(
                    actionComboBox.getValue());
            Offense offenseObj = offenseDao.findByName(
                    offenseTypeComboBox.getValue());

            record.getEnrollment().setEnrollmentId(enrollmentID);
            record.getAction().setActionId(actionID);
            record.setOffense(offenseObj);
            record.setRemarks(remarksTextArea.getText());
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
                System.out.println("Violation Updated!");
                Stage stage = (Stage) (
                        (Node) event.getSource()).getScene().getWindow();
                stage.close();
                if (viewOffenseModalStage != null) {
                    viewOffenseModalStage.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to launch the reusable confirmation dialog.
     *
     * @param line1  The first line of the prompt message.
     * @param line2  The second line of the prompt message.
     * @param action The logic to execute if the user confirms.
     */
    private void showConfirmation(String line1, String line2, Runnable action) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dialogs/confirmation.fxml"));
            Parent root = loader.load();
            ConfirmationDialogController controller = loader.getController();
            controller.setMessage(line1, line2);
            controller.setButtonLabels("Yes", "No");
            controller.setOnConfirm(action);

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
