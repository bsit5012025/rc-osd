package org.rocs.osd.controller.student;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.rocs.osd.data.dao.guardian.GuardianDao;
import org.rocs.osd.data.dao.guardian.impl.GuardianDaoImpl;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.studentguardian.StudentGuardian;
import org.rocs.osd.model.record.Record;

import java.sql.Date;
import java.util.List;

public class StudentRecordController {
    /**
     * Text field for full name.
     */
    @FXML
    private TextField fullNameTextField;
    /**
     * Dropdown for grade or section.
     */
    @FXML
    private ComboBox<String> gradeComboBox;
    /**
     * Dropdown for disciplinary action status.
     */
    @FXML
    private ComboBox<String> statusComboBox;
    /**
     * Text field for section.
     */
    @FXML
    private TextField sectionTextField;
    /**
     * Text field for academic year.
     */
    @FXML
    private TextField academicYearTextField;
    /**
     * Text field for full name of guardian.
     */
    @FXML
    private TextField guardianTextField;
    /**
     * Text field for contact number.
     */
    @FXML
    private TextField contactNumberTextField;
    /**
     * Text field for address.
     */
    @FXML
    private TextField addressTextField;
    /**
     * Check box for intern.
     */
    @FXML
    private CheckBox internCheckBox;
    /**
     * Check box for extern.
     */
    @FXML
    private CheckBox externCheckBox;
    /**
     * Table for history offense.
     */
    @FXML
    private TableView<Record> offenseHistoryTable;
    /**
     * Table column of offense type.
     */
    @FXML
    private TableColumn<Record, String> offenseTypeColumn;
    /**
     * Table column of offense level.
     */
    @FXML
    private TableColumn<Record, String> levelOfOffenseColumn;
    /**
     * Table column of date.
     */
    @FXML
    private TableColumn<Record, Date> dateColumn;
    /**
     * Object for enrollment model.
     */
    private Enrollment enrollment;
    /**
     * DAO for guardian.
     */
    private GuardianDao guardianDao = new GuardianDaoImpl();
    /**
     * Facade for record.
     */
    private RecordFacade recordFacade =
            new RecordFacadeImpl(new RecordDaoImpl());
    /**
     * Sets the student enrollment data.
     *
     * @param studentEnrollment the enrollment object
     */
    public void setStudentData(Enrollment studentEnrollment) {
        this.enrollment = studentEnrollment;
        loadData();
        loadOffenseHistory();
    }
    /**
     * Loads student information into UI components.
     */
    public void loadData() {
        fullNameTextField.setText(
                enrollment.getStudent().getFirstName()
                        + " "
                        + enrollment.getStudent().getMiddleName()
                        + " "
                        + enrollment.getStudent().getLastName()
        );
        gradeComboBox.setValue(enrollment.getStudentLevel());
        sectionTextField.setText(enrollment.getSection());
        academicYearTextField.setText(enrollment.getSchoolYear());
        addressTextField.setText(enrollment.getStudent().getAddress());

        String studentType = enrollment.getStudent().getStudentType();
        String studentId = enrollment.getStudent().getStudentId();

        List<StudentGuardian> guardian =
                guardianDao.findGuardianByStudentId(studentId);

        Guardian primaryGuardian =
                guardian.get(0).getGuardian();

        guardianTextField.setText(primaryGuardian
                .getFirstName()
                + " "
                + primaryGuardian.getLastName());
        contactNumberTextField.setText(primaryGuardian.getContactNumber());
        statusComboBox.setValue(enrollment.getDisciplinaryStatus().getStatus());

        if ("Intern".equalsIgnoreCase(studentType)) {
            internCheckBox.setSelected(true);
            externCheckBox.setSelected(false);
            externCheckBox.setDisable(true);
        } else if ("Extern".equalsIgnoreCase(studentType)) {
            externCheckBox.setSelected(true);
            internCheckBox.setSelected(false);
            internCheckBox.setDisable(true);
        }
    }
    /**
     * Loads offense history into the table.
     */
    public void loadOffenseHistory() {
        String studentId = enrollment.getStudent().getStudentId();

        List<Record> records =
                recordFacade.getRecordByStudentId(studentId);

        offenseTypeColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue()
                                .getOffense()
                                .getOffense()));

        levelOfOffenseColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue()
                                .getOffense()
                                .getType()));

        dateColumn.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(
                        new Date(
                                cell.getValue()
                                        .getDateOfViolation()
                                        .getTime())));

        offenseHistoryTable.setItems(FXCollections
                .observableArrayList(records));
    }
    /**
     * Closes the current window.
     *
     * @param event the action event
     */
    public void onCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
