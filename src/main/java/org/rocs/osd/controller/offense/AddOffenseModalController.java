package org.rocs.osd.controller.offense;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.data.dao.enrollment.impl.EnrollmentDaoImpl;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.data.dao.offense.impl.OffenseDaoImpl;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.data.dao.student.StudendDao;
import org.rocs.osd.data.dao.student.impl.StudentDaoImpl;
import org.rocs.osd.facade.Record.RecordFacade;
import org.rocs.osd.facade.Record.impl.RecordFacadeImpl;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;

import java.sql.Date;

/**
 * Controller for the "Add Offense" modal in the Office of Student Discipline System.
 * This class handles the population of offense type and level ComboBoxes and automatically selects the level of offense based on the selected offense type.
 */
public class AddOffenseModalController {

    @FXML private ComboBox<String> offenseTypeComboBox;
    @FXML private ComboBox<String> levelOfOffenseComboBox;
    @FXML private TextField studentIdTextField;
    @FXML private TextField studentNameTextField;
    @FXML private DatePicker datePicker;
    @FXML private TextArea remarksTextArea;

    private StudendDao studentDao;
    private OffenseDao offenseDao;
    private RecordFacade recordFacade;
    private EnrollmentDao enrollmentDao;

    /**
     * Controller for the "Add Offense" modal.
     * Handles the population of offense type and level ComboBoxes and automatically selects the level of offense based on the selected offense type.
     */
    public void initialize(){
        offenseDao = new OffenseDaoImpl();
        studentDao = new StudentDaoImpl();
        RecordDao dao = new RecordDaoImpl();
        recordFacade = new RecordFacadeImpl(dao);
        enrollmentDao = new EnrollmentDaoImpl();

        loadComboBoxData();
        autoSelectLevelOfOffense();
        studentIdTextField.setOnAction(e -> autoDisplayStudentName());
    }

    /**
     * Loads all offense names from the database into the offense type ComboBox.
     * Prints an error message if the database fetch fails.
     */

    public void loadComboBoxData(){
        ObservableList<String> offenseList = FXCollections.observableArrayList(offenseDao.findAllOffenseName());
        offenseTypeComboBox.setItems(offenseList);
        try {
            offenseDao = new OffenseDaoImpl();
            var data = offenseDao.findAllOffenseName();
            if (data != null) {
                offenseTypeComboBox.setItems(FXCollections.observableArrayList(data));
            }
        } catch (Exception e) {
            System.err.println("Database Error: Could not fetch offense names from the database.");
        }

    }
    /**
     * Automatically selects the level of offense based on the offense type chosen by the user.
     * When a user selects an offense type, the corresponding level is displayed in the level ComboBox.
     */
    public void autoSelectLevelOfOffense() {

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
    public void onCancel(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onSubmit(ActionEvent event){
        String studentId = studentIdTextField.getText();
        String studentName = studentNameTextField.getText();
        String offenseType = offenseTypeComboBox.getValue();
        String offenseLevel = levelOfOffenseComboBox.getValue();
        String remarks = remarksTextArea.getText();
        Date dateOfViolation = java.sql.Date.valueOf(datePicker.getValue());
        Offense offenseId = offenseDao.findByName(offenseType);
        long enrollmentId = enrollmentDao.findEnrollmentIdByStudentId(studentId);
        String employeeId = "";

        boolean success = recordFacade.createStudentRecord(enrollmentId,employeeId,offenseId.getOffenseId(),
                dateOfViolation,remarks);

    }
}
