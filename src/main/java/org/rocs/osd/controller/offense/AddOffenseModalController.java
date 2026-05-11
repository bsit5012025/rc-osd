package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.rocs.osd.controller.dialog.ConfirmationDialogController;
import org.rocs.osd.controller.sms.SmsService;
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
import org.rocs.osd.facade.guardian.impl.GuardianFacadeImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.person.student.guardian.StudentGuardian;
import org.rocs.osd.facade.guardian.GuardianFacade;
import static org.rocs.osd.controller.sms.SmsService.formatPhone;

import java.io.IOException;
import java.sql.Date;

/**
 * Controller for the "Add Offense"
 * modal in the Office of Student Discipline System.
 * This class handles the population of
 * offense type and level ComboBoxes and
 * manages the recording of student violations including SMS notifications.
 */
public class AddOffenseModalController {

    /** Dropdown for selecting offense type. */
    @FXML
    private ComboBox<String> offenseTypeComboBox;

    /** Dropdown for selecting disciplinary action. */
    @FXML
    private ComboBox<String> actionComboBox;

    /** Text field for displaying offense level. */
    @FXML
    private TextField levelOfOffense;

    /** Input field for student ID. */
    @FXML
    private TextField studentIdTextField;

    /** Input field for student name. */
    @FXML
    private TextField studentNameTextField;

    /** Date picker for selecting violation date. */
    @FXML
    private DatePicker datePicker;

    /** Text area for remarks input. */
    @FXML
    private TextArea remarksTextArea;

    /** Checkbox for notifying parents/guardian. */
    @FXML
    private CheckBox notifyParentsCheckBox;

    /** DAO for student operations. */
    private StudendDao studentDao;

    /** DAO for offense operations. */
    private OffenseDao offenseDao;

    /** Facade for record operations. */
    private RecordFacade recordFacade;

    /** DAO for disciplinary actions. */
    private DisciplinaryActionDao disciplinaryActionDao;

    /** DAO for enrollment operations. */
    private EnrollmentDao enrollmentDao;

    /** Facade for guardian actions. */
    private GuardianFacade guardianFacade;

    /**
     * Initializes the controller, sets up DAOs and Facades, and
     * triggers initial data loading.
     */
    public void initialize() {
        offenseDao = new OffenseDaoImpl();
        studentDao = new StudentDaoImpl();
        RecordDao dao = new RecordDaoImpl();
        disciplinaryActionDao = new DisciplinaryActionImpl();
        recordFacade = new RecordFacadeImpl(dao);
        enrollmentDao = new EnrollmentDaoImpl();
        guardianFacade = new GuardianFacadeImpl();
        loadComboBoxData();
        autoSelectLevelOfOffense();
        studentIdTextField.setOnAction(e -> autoDisplayStudentName());
    }

    /**
     * Loads all offense names and disciplinary actions from the database
     * into their respective ComboBoxes.
     */
    public void loadComboBoxData() {
        try {
            ObservableList<String>
                    offenseList = FXCollections.observableArrayList(
                    offenseDao.findAllOffenseName());
            ObservableList<String>
                    actionList = FXCollections.observableArrayList(
                    disciplinaryActionDao.findAllAction());
            offenseTypeComboBox.setItems(offenseList);
            actionComboBox.setItems(actionList);
        } catch (Exception e) {
            System.err.println(
                    "Database Error: Could not fetch offense names.");
        }
    }

    /**
     * Automatically selects the level of
     * offense based on the offense type chosen by the user.
     */
    public void autoSelectLevelOfOffense() {
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
     * Displays the student name based on the entered student ID.
     */
    @FXML
    private void autoDisplayStudentName() {
        String studentId = studentIdTextField.getText();
        if (studentId.isEmpty()) {
            return;
        }

        Student student = studentDao.findStudentWithRecordById(studentId);
        if (student.getStudentId() != null) {
            studentNameTextField.setText(student.getFirstName() + " "
                    + student.getMiddleName() + " " + student.getLastName());
        } else {
            studentNameTextField.clear();
        }
    }

    /**
     * Validates required input fields and opens
     * a confirmation dialog to save the offense.
     * If all fields are populated, the user is prompted to confirm the action.
     * The record is only saved to the database
     * if the user clicks the "Submit" button.
     * @param event the action event triggered by clicking the submit button.
     */
    @FXML
    public void onSubmit(ActionEvent event) {
        if (studentIdTextField.getText().isEmpty()
                || studentNameTextField.getText().isEmpty()
                || offenseTypeComboBox.getValue() == null
                || actionComboBox.getValue() == null
                || datePicker.getValue() == null) {
            System.out.println("Fill out missing fields!");
            return;
        }

        showConfirmation(
                "Are you sure you want to",
                "add this violation?",
                "Submit", // Confirm Button
                "Cancel", // Cancel Button
                this::saveOffenseRecord
        );
    }

    /**
     * Performs the actual database save operation for the offense record.
     * Executes after the user confirms the action in the dialog.
     */
    private void saveOffenseRecord() {
        try {
            String sId = studentIdTextField.getText();
            String sName = studentNameTextField.getText();
            String oType = offenseTypeComboBox.getValue();
            String aName = actionComboBox.getValue();
            Date dOv = Date.valueOf(datePicker.getValue());

            long oId = offenseDao.findByName(oType).getOffenseId();
            long aId = disciplinaryActionDao.findActionIdByName(aName);
            long eId = enrollmentDao.findEnrollmentIdByStudentId(sId);

            boolean success = recordFacade.createStudentRecord(
                    eId,
                    "EMP-002",
                    oId,
                    dOv,
                    aId,
                    remarksTextArea.getText()
            );

            if (success) {
                if (notifyParentsCheckBox.isSelected()) {
                    handleSmsNotification(sId, sName, oType);
                }
                System.out.println("Violation recorded!");
                ((Stage) studentIdTextField.getScene().getWindow()).close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles fetching guardian contact information
     * and sending an asynchronous SMS notification.
     * @param studentId   The ID of the student.
     * @param studentName The full name of the student.
     * @param offenseType The name of the offense committed.
     */
    private void handleSmsNotification(String studentId,
                                       String studentName, String offenseType) {
        try {
            var studentGuardians = guardianFacade
                    .getGuardianByStudentId(studentId);
            if (studentGuardians == null || studentGuardians.isEmpty()) {
                System.out.println("No guardians found.");
                return;
            }

            for (StudentGuardian sg : studentGuardians) {
                Guardian guardian = sg.getGuardian();
                if (guardian == null || guardian.getContactNumber()
                        == null) {
                    continue;
                }

                String phone = formatPhone(guardian.getContactNumber());
                String message
                        = "Good day!\nI am the Discipline Officer "
                        + "from Rogationist College. "
                        + "This is to inform you that your child " + studentName
                        + ", committed an offense: \n\n" + offenseType
                        + "\n\nPlease coordinate with us "
                        + "thru call or in-person. "
                        + "Thank you!";

                SmsService.sendSMSAsync(phone, message);
                System.out.println("SMS sent to: " + phone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to launch the reusable
     * confirmation dialog with custom labels.
     * @param line1        The first line of the prompt message.
     * @param line2        The second line of the prompt message.
     * @param confirmLabel The text for the confirm button.
     * @param cancelLabel  The text for the cancel button.
     * @param action       The logic to execute if the user confirms.
     */
    private void showConfirmation(
            String line1, String line2, String confirmLabel,
            String cancelLabel, Runnable action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/dialogs/confirmation.fxml"));
            Parent root = loader.load();
            ConfirmationDialogController controller = loader.getController();
            controller.setMessage(line1, line2);

            controller.setButtonLabels(confirmLabel, cancelLabel);
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

    /**
     * Triggers a confirmation dialog when the cancel button is clicked.
     * If the user confirms (clicks "Yes"), the current modal is closed
     * and any unsaved changes are discarded.
     * @param event the action event triggered by the cancel button.
     */
    @FXML
    public void onCancel(ActionEvent event) {
        showConfirmation(
                "Are you sure you want to",
                "cancel?",
                "Yes",
                "No",
                () -> {
                    Stage stage = (Stage) (
                            (Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
        );
    }
}
