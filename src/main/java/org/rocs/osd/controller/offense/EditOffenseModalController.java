package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.rocs.osd.data.dao.disciplinaryAction.DisciplinaryActionDao;
import org.rocs.osd.data.dao.disciplinaryAction.impl.DisciplinaryActionImpl;
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

import java.sql.Date;
import java.time.LocalDate;

/**
 * Controller for editing an existing offense record.
 * Handles loading, displaying, and updating student violation details.
 */
public class EditOffenseModalController
{
    @FXML
    private ComboBox<String> offenseTypeComboBox;

    @FXML
    private ComboBox<String> levelOfOffenseComboBox;

    @FXML
    private TextField studentIdTextField;

    @FXML
    private TextField studentNameTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField remarksTextArea;

    private StudendDao studentDao;
    private OffenseDao offenseDao;
    private EnrollmentDao enrollmentDao;
    private RecordFacade recordFacade;
    private DisciplinaryActionDao disciplinaryActionDao;
    private Record record;

    /**
     * Initializes DAO, Facade, and UI components.
     */
    public void initialize()
    {
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
     * Sets the record data to be edited.
     */
    public void setRecordData(Record record)
    {
        this.record = record;
        loadStudentRecordInfo();
    }

    private void loadStudentRecordInfo()
    {
        studentIdTextField.setText(
                record.getEnrollment().getStudent().getStudentId()
        );

        studentNameTextField.setText(
                record.getEnrollment().getStudent().getFirstName() + " " +
                        record.getEnrollment().getStudent().getMiddleName() + " " +
                        record.getEnrollment().getStudent().getLastName()
        );

        datePicker.setValue(LocalDate.parse(String.valueOf(record.getDateOfViolation())));
        offenseTypeComboBox.setValue(record.getOffense().getOffense());
        levelOfOffenseComboBox.setValue(record.getOffense().getType());
        remarksTextArea.setText(record.getRemarks());
    }

    private void loadComboBoxData(){

        try {
            ObservableList<String> offenseList = FXCollections.observableArrayList(offenseDao.findAllOffenseName());
            ObservableList<String> actionList = FXCollections.observableArrayList(disciplinaryActionDao.findAllAction());
            offenseTypeComboBox.setItems(offenseList);
        } catch (Exception e) {
            System.err.println("Database Error: Could not fetch offense names from the database.");
        }
    }

    private void autoSelectLevelOfOffense() {

        offenseTypeComboBox.setOnAction(event -> {

            String selected = offenseTypeComboBox.getValue();

            if (selected != null) {

                Offense offense = offenseDao.findByName(selected);

                if (offense != null) {
                    levelOfOffenseComboBox.setValue(offense.getType());
                }
            }
        });
    }

    @FXML
    private void autoDisplayStudentName() {

        String studentId = studentIdTextField.getText();

        if (studentId.isEmpty()) return;

        Student student = studentDao.findStudentWithRecordById(studentId);

        if (student.getStudentId() != null) {
            String fullName = student.getFirstName() + " " +
                    student.getMiddleName() + " " +
                    student.getLastName();

            studentNameTextField.setText(fullName);
        } else {
            studentNameTextField.clear();
            System.out.println("STUDENT NOT FOUND!");
        }
    }

    @FXML
    private void onCancel(ActionEvent event)
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSubmit(ActionEvent event)
    {
        try
        {
            String studentId = studentIdTextField.getText();
            String studentName = studentNameTextField.getText();
            String offenseName = offenseTypeComboBox.getValue();
            String offenseType = levelOfOffenseComboBox.getValue();
            String remarks = remarksTextArea.getText();

            if (studentId == null
                    || studentId.isEmpty()
                    || studentName == null
                    || studentName.isEmpty()
                    || offenseName == null
                    || offenseType == null
                    || datePicker.getValue() == null)
            {

                System.out.println("Fill out all required fields!");
                return;
            }

            if (record == null
                    || record.getEnrollment() == null
                    || record.getAction() == null
                    || record.getEnrollment().getStudent() == null)
            {
                System.out.println("Record, Enrollment, Action or student are missing or null!");
                return;
            }

            Date dateOfViolation = java.sql.Date.valueOf(datePicker.getValue());

            long enrollmentID = enrollmentDao.findEnrollmentIdByStudentId(studentId);
            record.getEnrollment().setEnrollmentId(enrollmentID);

            long actionId = disciplinaryActionDao.findActionIdByName(record.getAction().getActionName());
            record.getAction().setActionId(actionId);

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

            if(status){
                System.out.println("Violation Updated!");

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
