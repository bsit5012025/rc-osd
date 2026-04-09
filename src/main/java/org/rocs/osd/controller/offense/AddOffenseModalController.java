package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
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
 * Controller for the "Add Offense" modal in the Office of
 * Student Discipline System.
 * This class handles the population of offense type
 * and level ComboBoxes and automatically selects the
 * level of offense based on the selected offense type.
 */
public class AddOffenseModalController {

    /**
     * Dropdown for selecting the type of offense.
     */
    @FXML
    private ComboBox<String> offenseTypeComboBox;

    /**
     * Dropdown for selecting the disciplinary action to be taken.
     */
    @FXML
    private ComboBox<String> actionComboBox;

    /**
     * Display field for the severity level of the selected offense.
     */
    @FXML
    private TextField levelOfOffense;

    /**
     * Input field for the student's unique identification number.
     */
    @FXML
    private TextField studentIdTextField;

    /**
     * Display field for the full name of the student.
     */
    @FXML
    private TextField studentNameTextField;

    /**
     * Selection tool for the date the violation occurred.
     */
    @FXML
    private DatePicker datePicker;

    /**
     * Text area for providing additional details or context about the offense.
     */
    @FXML
    private TextArea remarksTextArea;

    /**
     * Option to trigger an automated SMS
     * notification to the student's guardian.
     */
    @FXML
    private CheckBox notifyParentsCheckBox;

    /**
     * Data Access Object for student-related database operations.
     */
    private StudendDao studentDao;

    /**
     * Data Access Object for offense-related database operations.
     */
    private OffenseDao offenseDao;

    /**
     * Facade providing high-level operations for offense records.
     */
    private RecordFacade recordFacade;

    /**
     * Data Access Object for disciplinary action database operations.
     */
    private DisciplinaryActionDao disciplinaryActionDao;

    /**
     * Data Access Object for student enrollment database operations.
     */
    private EnrollmentDao enrollmentDao;

    /**
     * Facade providing high-level operations for guardian-related actions.
     */
    private GuardianFacade guardianFacade;

    /**
     * Initializes DAOs, Facades, and UI listeners.
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
     * Populates ComboBoxes with data from the database.
     */
    public void loadComboBoxData() {
        try {
            offenseTypeComboBox.setItems(
                    FXCollections.observableArrayList(
                            offenseDao.findAllOffenseName()));
            actionComboBox.setItems(
                    FXCollections.observableArrayList(
                            disciplinaryActionDao.findAllAction()));
        } catch (Exception e) {
            System.err.println(
                    "Database Error: Could not fetch ComboBox data.");
        }
    }

    /**
     * Fetches and displays student name based on the ID entered.
     */
    @FXML
    private void autoDisplayStudentName() {
        String studentId = studentIdTextField.getText();
        if (studentId.isEmpty()) {
            return;
        }
        Student student = studentDao.findStudentWithRecordById(studentId);
        if (student != null && student.getStudentId() != null) {
            String fullName = student.getFirstName()
                    + " " + student.getMiddleName()
                    + " " + student.getLastName();
            studentNameTextField.setText(fullName);
        } else {
            studentNameTextField.clear();
        }
    }

    /**
     * Helper method to launch the reusable confirmation dialog.
     *
     * @param line1  The first line of the prompt message.
     * @param line2  The second line of the prompt message.
     * @param action The logic to execute if the user confirms.
     */
    private void showConfirmation(
            String line1, String line2, Runnable action) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/dialogs/confirmation.fxml"));
            Parent root = loader.load();

            ConfirmationDialogController controller = loader.getController();
            controller.setMessage(line1, line2);
            controller.setButtonLabels("Submit", "Cancel");
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
     * Updates the offense level text field when a type is selected.
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
     * Closes the modal window without saving any changes.
     *
     * @param event The action event triggered by the cancel button.
     */
    public void onCancel(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Validates input fields and triggers the confirmation dialog.
     *
     * @param event The action event triggered by the submit button.
     */
    @FXML
    public void onSubmit(ActionEvent event) {
        if (studentIdTextField.getText().isEmpty()
                || offenseTypeComboBox.getValue() == null
                || datePicker.getValue() == null) {
            System.out.println("Fill out missing fields!");
            return;
        }

        showConfirmation(
                "Are you sure you want to",
                "add this violation?",
                this::saveOffenseRecord
        );
    }

    /**
     * Saves the offense record and triggers SMS notification.
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
                handleSmsNotification(sId, sName, oType);
                ((Stage) studentIdTextField.getScene().getWindow()).close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Sends SMS to guardians if the notify checkbox is checked.
     *
     * @param studentId   The ID of the student.
     * @param studentName The full name of the student.
     * @param offenseType The type of offense committed.
     */
    private void handleSmsNotification(
            String studentId, String studentName, String offenseType) {
        if (!notifyParentsCheckBox.isSelected()) {
            return;
        }

        try {
            var guardians = guardianFacade.getGuardianByStudentId(studentId);
            if (guardians == null) {
                return;
            }

            for (StudentGuardian sg : guardians) {
                Guardian g = sg.getGuardian();
                if (g != null && g.getContactNumber() != null) {
                    String msg = "Discipline Office: Your child, "
                            + studentName + ", committed: " + offenseType + ".";
                    SmsService.sendSMSAsync(
                            formatPhone(g.getContactNumber()), msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Violation recorded!");
    }
}
