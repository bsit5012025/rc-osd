package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
import org.rocs.osd.model.person.studentguardian.StudentGuardian;
import org.rocs.osd.facade.guardian.GuardianFacade;
import static org.rocs.osd.controller.sms.SmsService.formatPhone;

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
     * Dropdown for selecting offense type.
     */
    @FXML
    private ComboBox<String> offenseTypeComboBox;
    /**
     * Dropdown for selecting disciplinary action.
     */
    @FXML
    private ComboBox<String> actionComboBox;
    /**
     * Text field for displaying offense level.
     */
    @FXML
    private TextField levelOfOffense;
    /**
     * Input field for student ID.
     */
    @FXML
    private TextField studentIdTextField;
    /**
     * Input field for student name.
     */
    @FXML
    private TextField studentNameTextField;
    /**
     * Date picker for selecting violation date.
     */
    @FXML
    private DatePicker datePicker;
    /**
     * Text area for remarks input.
     */
    @FXML
    private TextArea remarksTextArea;
    /**
     * Checkbox for notifying parents/guardian.
     * */
    @FXML
    private CheckBox notifyParentsCheckBox;
    /**
     * DAO for student operations.
     */
    private StudendDao studentDao;
    /**
     * DAO for offense operations.
     */
    private OffenseDao offenseDao;
    /**
     * Facade for record operations.
     */
    private RecordFacade recordFacade; /**
     * DAO for disciplinary actions.
     */
    private DisciplinaryActionDao disciplinaryActionDao;
    /**
     * DAO for enrollment operations.
     */
    private EnrollmentDao enrollmentDao;
    /**
     * Facade for guardian actions.
     * */
    private GuardianFacade guardianFacade;
    /**
     * Controller for the "Add Offense" modal.
     * Handles the population of offense type and level ComboBoxes and
     * automatically selects the level of offense based on
     * the selected offense type.
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
     * Loads all offense names from the database
     * into the offense type ComboBox.
     * Prints an error message if the database fetch fails.
     */
    public void loadComboBoxData() {

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
     * Automatically selects the level of offense
     * based on the offense type chosen by the user.
     * When a user selects an offense type,
     * the corresponding level is displayed in the level ComboBox.
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
     * @param event action event from cancel button.
     */
    public void onCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    /**
     * Handles submission of offense form.
     * Validates input and creates a new record.
     *
     * @param event action event from submit button.
     */
    public void onSubmit(ActionEvent event) {
        try {
            String studentId = studentIdTextField.getText();
            String studentName = studentNameTextField.getText();
            String offenseType = offenseTypeComboBox.getValue();
            String actionName = actionComboBox.getValue();
            String remarks = remarksTextArea.getText();

            if (studentId.isEmpty()
                    || studentName.isEmpty()
                    || offenseType == null
                    || actionName == null
                    || datePicker.getValue() == null) {
                System.out.println("Fill out missing fields!");
                return;
            }

            Date dateOfViolation = Date.valueOf(datePicker.getValue());
            String employeeId = "EMP-002";
            Offense offense = offenseDao.findByName(offenseType);
            long offenseId = offense.getOffenseId();
            long actionID = disciplinaryActionDao.
                    findActionIdByName(actionName);
            long enrollmentId = enrollmentDao.
                    findEnrollmentIdByStudentId(studentId);

            boolean record = recordFacade.createStudentRecord(
                    enrollmentId,
                    employeeId,
                    offenseId,
                    dateOfViolation,
                    actionID,
                    remarks
            );
            if (record) {
                if (notifyParentsCheckBox.isSelected()) {
                    try {
                        var studentGuardians =
                                guardianFacade.getGuardianByStudentId(
                                        studentId);

                        if (studentGuardians == null
                                || studentGuardians.isEmpty()) {
                            System.out.println("No guardians found.");
                            return;
                        }

                        for (StudentGuardian sg : studentGuardians) {

                            Guardian guardian = sg.getGuardian();
                            if (guardian == null
                                    || guardian.getContactNumber() == null) {
                                continue;
                            }

                            String phone = guardian.getContactNumber();
                            phone = formatPhone(phone);

                            String message = "Good day!\nI am (NAME) "
                                    + "Discipline Officer from "
                                    + "Rogationist College. This is to inform "
                                    + "you that your child "
                                    + studentName
                                    + ", committed an offense: \n\n"
                                    + offenseType
                                    + "\n \n"
                                    + "Please coordinate with us thru call or "
                                    + "in-person to settle this matter. "
                                    + "Thank you!";

                            SmsService.sendSMSAsync(phone, message);

                            System.out.println("SMS sent to: "
                                    + phone);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Stage stage = (Stage) ((Node) event
                        .getSource()).getScene().getWindow();
                stage.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Violation recorded!");
    }
}
