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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.rocs.osd.data.dao.disciplinary.status.DisciplinaryStatusDao;
import org.rocs.osd.data.dao.disciplinary.status.impl.DisciplinaryStatusDaoImpl;
import org.rocs.osd.data.dao.enrollment.impl.EnrollmentDaoImpl;
import org.rocs.osd.data.dao.guardian.GuardianDao;
import org.rocs.osd.data.dao.guardian.impl.GuardianDaoImpl;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.data.dto.student.report.StudentReportDTO;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.facade.enrollment.impl.EnrollmentFacadeImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.disciplinary.status.DisciplinaryStatus;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.student.guardian.StudentGuardian;
import org.rocs.osd.model.record.Record;

import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Checkbox for saving current
     * selected value on statusComboBox.
     */
    @FXML
    private CheckBox statusSave;
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
     * Controller Class Instance.
     */
    private StudentController studentController =
            new StudentController();
    /**
     * Facade for Enrollment.
     */
    private EnrollmentFacade enrollmentFacade =
            new EnrollmentFacadeImpl(new EnrollmentDaoImpl());
    /**
     * DAO for guardian.
     */
    private GuardianDao guardianDao = new GuardianDaoImpl();
    /**
     *  DAO for Disciplinary DAO.
     */
    private DisciplinaryStatusDao disciplinaryStatusDao =
            new DisciplinaryStatusDaoImpl();
    /**
     * Facade for record.
     */
    private RecordFacade recordFacade =
            new RecordFacadeImpl(new RecordDaoImpl());

    /**
     * Stores the disciplinary statuses returned by the query.
     */
    private List<DisciplinaryStatus> arrayStatus;
    /**
     * Sets the student enrollment data.
     *
     * @param studentEnrollment the enrollment object
     */
    public void setStudentData(Enrollment studentEnrollment) {
        this.enrollment = studentEnrollment;
        loadData();
        setOffenseData();
    }

    /**
     * Sets the StudentController instance to activate
     * the refresh method for the table view.
     *
     * @param pstudentController the StudentController
     *                           instance to be used.
     */
    public void setStudentController(
            StudentController pstudentController) {
        studentController = pstudentController;
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
        gradeComboBox.setOnAction(event -> {
            setOffenseDataByStudentLevel();
        });
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

        arrayStatus = disciplinaryStatusDao.getAllDisciplinaryStatus();

        if (statusComboBox.getItems().isEmpty()) {
            for (DisciplinaryStatus status : arrayStatus) {
                statusComboBox.getItems().add(status.getStatus());
            }
        }

        internCheckBox.setMouseTransparent(true);
        externCheckBox.setMouseTransparent(true);

        if ("Intern".equalsIgnoreCase(studentType)) {
            internCheckBox.setSelected(true);
            externCheckBox.setSelected(false);
        } else if ("Extern".equalsIgnoreCase(studentType)) {
            internCheckBox.setSelected(false);
            externCheckBox.setSelected(true);
        }
    }

    /**
     * Updates the selected disciplinary status for a student's enrollment
     * in the specified school year.
     *
     * @param statusID the ID of the disciplinary status to assign.
     * @param studentID the unique identifier of the student.
     * @param schoolYear the school year of the student's enrollment
     */
    private void selectedStatus(long statusID,
                                String studentID,
                                String schoolYear) {
        enrollmentFacade.setDisciplinaryStatusID(
                statusID, studentID, schoolYear
        );
    }

    /**
     * Triggered when the user selects a student's grade level.
     * Displays the student's offenses for the selected grade level.
     * If the student was not enrolled during that school year,
     * the student's information is displayed accordingly.
     */
    private void setOffenseDataByStudentLevel() {
        String studentLevel = gradeComboBox.getValue();

        if (studentLevel.contains("Grade")) {
            studentLevel = studentLevel.replace(" ", "-");
        }

        Enrollment studentInfo = enrollmentFacade.
        getEnrollmentsByStudentLevelAndName(
                studentLevel,
                enrollment.getStudent().getFirstName(),
                enrollment.getStudent().getMiddleName(),
                enrollment.getStudent().getLastName()
        );

        if (studentInfo == null) {
            offenseHistoryTable.getItems().clear();
            statusComboBox.setValue("");
            academicYearTextField.setText("");
            sectionTextField.setText("");
            return;
        }

        enrollment = studentInfo;
        loadData();

        List<Record> records =
                recordFacade.getRecordByStudentLevel(
                    studentLevel,
                    enrollment.getStudent().getFirstName(),
                    enrollment.getStudent().getMiddleName(),
                    enrollment.getStudent().getLastName()
        );
        loadOffenseHistory(records);
    }

    /**
     *
     */
    private void setOffenseData() {
        String studentId = enrollment.getStudent().getStudentId();

        List<Record> records =
                recordFacade.getRecordByStudentId(studentId);
        loadOffenseHistory(records);
    }

    /**
     * Loads offense history into the table.
     *
     * @param records For usability.
     */
    public void loadOffenseHistory(List<Record> records) {

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
     * File chooser for the onDownload. Filename format
     * is (StudentID_Surname_Discipline_Report) as a PDF.
     * User can freely type the filename and displays
     * available file types. (Only PDF)
     * @return fileChooser
     * */
    private FileChooser getFileChooser() {
        FileChooser fileChooser =
                new FileChooser();
        fileChooser.setTitle("Save Discipline Sheet");
        FileChooser.ExtensionFilter pdfFilter =
                new FileChooser.ExtensionFilter(
                        "PDF Files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfFilter);
        fileChooser.setSelectedExtensionFilter(pdfFilter);

        String studentId = enrollment.getStudent().getStudentId().toString();
        String surname = enrollment.getStudent().getLastName();
        String defaultFileName = studentId
                + "_" + surname
                + "_"
                + "Discipline_Report.pdf";

        fileChooser.setInitialFileName(defaultFileName);
        return fileChooser;
    }

    /**
     * Downloads the selected student data to desired directory.
     * Automatically opens file when the user downloads the PDF.
     * */
    public void onDownload() {
        FileChooser fileChooser = getFileChooser();
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf");

        Stage stage = (Stage) fullNameTextField.getScene().getWindow();
        File outputFile = fileChooser.showSaveDialog(stage);

        if (outputFile != null) {
            try (InputStream reportStream = getClass().getResourceAsStream(
                    "/reports/StudentReport.jasper")) {

                List<Record> records = recordFacade.getRecordByStudentId(
                        enrollment.getStudent().getStudentId());

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("studentName", fullNameTextField.getText());
                parameters.put("grade", gradeComboBox.getValue());
                parameters.put("section", sectionTextField.getText());
                parameters.put("academicYear", academicYearTextField.getText());
                parameters.put("studentAddress", addressTextField.getText());
                parameters.put("guardianName", guardianTextField.getText());
                parameters.put("contactNumber",
                        contactNumberTextField.getText());
                parameters.put("guardianAddress", addressTextField.getText());
                parameters.put("status", statusComboBox.getValue());
                parameters.put("internCheckBox",
                        internCheckBox.isSelected() ? "X" : "");
                parameters.put("externCheckBox",
                        externCheckBox.isSelected() ? "X" : "");

                try (InputStream logo = getClass().getResourceAsStream(
                        "/reports/logo.png")) {
                    if (logo != null) {
                        parameters.put("logoStream", logo);
                    }

                    List<StudentReportDTO> tableData = new ArrayList<>();
                    for (Record record : records) {
                        StudentReportDTO row = new StudentReportDTO();
                        row.setOffenseType(record.getOffense().getOffense());
                        row.setLevelOfOffense(record.getOffense().getType());
                        row.setDate(record.getDateOfViolation().toString());
                        tableData.add(row);
                    }

                    JRBeanCollectionDataSource dataSource =
                            new
                                    JRBeanCollectionDataSource(tableData);

                    JasperPrint jasperPrint =
                            JasperFillManager.fillReport(
                                    reportStream, parameters, dataSource);

                    JasperExportManager.
                            exportReportToPdfFile(jasperPrint,
                            outputFile.getAbsolutePath()
                    );

                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(outputFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * Closes the current window.
     *
     * @param event the action event
     */
    public void onCancel(ActionEvent event) {

        if (statusSave.isSelected()) {

            for (DisciplinaryStatus status
                    : arrayStatus) {

                if (statusComboBox.getValue().
                        equals(status.getStatus())) {

                    selectedStatus(
                            status.getDisciplinaryStatusId(),
                            enrollment.getStudent().getStudentId(),
                            enrollment.getSchoolYear());

                } else if (arrayStatus == null) {
                    break;
                }
            }

            studentController.refreshTable();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
