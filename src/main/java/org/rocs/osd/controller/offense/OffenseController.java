package org.rocs.osd.controller.offense;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.data.dao.record.impl.RecordDaoImpl;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.department.Department;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * Controller for managing offenses in the
 * Office of Student Discipline System.
 * This class handles opening the "Add Offense" modal.
 */
public class OffenseController {
    /**
     * Table displaying list of violations.
     */
    @FXML
    private TableView<Record> violationsTable;
    /**
     * Column for student ID.
     */
    @FXML
    private TableColumn<Record, String> studentIdColumn;
    /**
     * Column for student name.
     */
    @FXML
    private TableColumn<Record, String> studentNameColumn;
    /**
     * Column for offense level.
     */
    @FXML
    private TableColumn<Record, String> offenseLevelColumn;
    /**
     * Column for offense type.
     */
    @FXML
    private TableColumn<Record, String> offenseTypeColumn;
    /**
     * Column for violation date.
     */
    @FXML
    private TableColumn<Record, String> dateColumn;
    /**
     * Column for status of violation.
     */
    @FXML
    private TableColumn<Record, String> statusColumn;
    /**
     * Label displaying current department.
     */
    @FXML
    private Label departmentLabel;
    /**
     * Search text field.
     */
    @FXML
    private TextField searchTextField;
    /**
     * Facade for accessing record data.
     */
    private RecordFacade recordFacade;
    /**
     * Currently selected department.
     */
    private Department currentdDepartment = Department.JHS;
    /**
     * Flag to prevent duplicate modal opens.
     */
    private static boolean isModalOpen = false;
    /**
     * Static controller factory for FXMLLoader.
     */
    private static Callback<Class<?>, Object> controllerFactory;
    /**
     * Static mock for add offense modal opener (for testing).
     */
    private static Runnable mockAddOffenseModal;
    /**
     * Static mock for view offense modal opener (for testing).
     */
    private static Consumer<Record> mockViewOffenseModal;


    /**
     * Sets the record facade for dependency injection.
     * Used for testing to inject mock facades.
     *
     * @param pRecordFacade the facade to use
     */
    public void setRecordFacade(RecordFacade pRecordFacade) {
        this.recordFacade = pRecordFacade;
    }

    /**
     * Gets the record facade, creating default implementation if not set.
     *
     * @return the record facade
     */
    private RecordFacade getRecordFacade() {
        if (recordFacade == null) {
            recordFacade = new RecordFacadeImpl(new RecordDaoImpl());
        }
        return recordFacade;
    }

    /**
     * Sets the controller factory for FXMLLoader.
     *
     * @param pFactory the controller factory
     */
    public static void setControllerFactory(
            Callback<Class<?>, Object> pFactory) {
        controllerFactory = pFactory;
    }

    /**
     * Clears the controller factory.
     */
    public static void clearControllerFactory() {
        controllerFactory = null;
    }

    /**
     * Sets mock add offense modal opener for testing.
     *
     * @param pMock the mock runnable
     */
    public static void setMockAddOffenseModal(Runnable pMock) {
        mockAddOffenseModal = pMock;
    }

    /**
     * Sets mock view offense modal opener for testing.
     *
     * @param pMock the mock consumer
     */
    public static void setMockViewOffenseModal(Consumer<Record> pMock) {
        mockViewOffenseModal = pMock;
    }

    /**
     * Clears static mocks and resets modal state.
     */
    public static void clearMocks() {
        mockAddOffenseModal = null;
        mockViewOffenseModal = null;
        isModalOpen = false;
    }

    /**
     * Opens the "Add Offense" modal as an
     * undecorated, non-resizable window.
     * @param event the ActionEvent triggered by the user.
     */
    public void onLoadOffenseModal(ActionEvent event) {
        if (isModalOpen) {
            return;
        }
        isModalOpen = true;
        if (mockAddOffenseModal != null) {
            mockAddOffenseModal.run();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("/view/offense/addOffenseModal.fxml"));
            if (controllerFactory != null) {
                loader.setControllerFactory(controllerFactory);
            }
            Parent root = loader.load();
            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setResizable(false);
            modalStage.setScene(new Scene(root));
            modalStage.setOnHidden(e -> {
                isModalOpen = false;
                refreshRecord();
            });
            modalStage.showAndWait();
            isModalOpen = false;
            refreshRecord();

        } catch (IOException e) {
            isModalOpen = false;
            System.err.println("UI Error: Could not find or load "
                    + "AddOffenseModal.fxml. "
                    + "Check the file path and Controller names.");
            e.printStackTrace();
        } catch (Exception e) {
            isModalOpen = false;
            System.err.println("Unexpected Error while opening modal: "
                    + e.getMessage());
        }
    }

    /**
     *  Opens the View Offense modal with selected record.
     *
     * @param record the selected record to edit
     */
    public void onLoadViewOffenseModal(Record record) {
        if (mockViewOffenseModal != null) {
            mockViewOffenseModal.accept(record);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/offense/viewStudentOffenseModal.fxml"));
            if (controllerFactory != null) {
                loader.setControllerFactory(controllerFactory);
            }
            Parent root = loader.load();

            ViewOffenseModalController controller = loader.getController();
            controller.setRecordData(record);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            modalStage.setOnHidden(e -> refreshRecord());

            modalStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes controller and loads initial data.
     */
    @FXML
    public void initialize() {
        if (recordFacade == null) {
            recordFacade = new RecordFacadeImpl(new RecordDaoImpl());
        }

        searchTextField.setOnAction(e ->
                searchForStudentViolation());

        loadDataToTable();
        selectStudentRecord();
        currentdDepartment = Department.JHS;
        loadRecordsOfViolation();
    }

    /**
     * Used for searching student names for
     * their related offenses.
     */
    private void searchForStudentViolation() {
        LocalDate now = LocalDate.now();
        String currentSchoolYear;
        int year = now.getYear();
        int month = now.getMonthValue();
        if (month >= 6) {
            currentSchoolYear = year + "-" + (year + 1);
        } else {
            currentSchoolYear = (year - 1) + "-" + year;
        }

        violationsTable.setItems(FXCollections.observableArrayList(recordFacade.
                getViolationsByDepartmentAndStudentName(
                        currentdDepartment, currentSchoolYear,
                        searchTextField.getText())));
    }

    /**
     * Sets up table column mappings.
     */
    private void loadDataToTable() {
        studentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue()
                        .getEnrollment()
                        .getStudent()
                        .getStudentId()));

        studentNameColumn.setCellValueFactory(cellData -> {
            var student = cellData.getValue()
                    .getEnrollment()
                    .getStudent();
            return new SimpleStringProperty(
                    student.getFirstName()
                            + " "
                            + student.getLastName());
        });

        offenseLevelColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.
                        getValue().getOffense().getType()));

        offenseTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.
                        getValue().getOffense().getOffense()));

        dateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().
                        getDateOfViolation().toString()));

        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getStatus().name()
                ));
    }

    /**
     * Loads violations filtered by department and school year.
     */
    private void loadRecordsOfViolation() {
        LocalDate now = LocalDate.now();
        String currentSchoolYear;
        int year = now.getYear();
        int month = now.getMonthValue();
        if (month >= 6) {
            currentSchoolYear = year + "-" + (year + 1);
        } else {
            currentSchoolYear = (year - 1) + "-" + year;
        }
        violationsTable.setItems(
                FXCollections.observableArrayList(
                        getRecordFacade().getViolationsByDepartment(
                                currentdDepartment, currentSchoolYear)));
    }
    /**
     * Handles row click to open edit modal.
     */
    private void selectStudentRecord() {
        violationsTable.setOnMouseClicked(event -> {
                Record record = violationsTable.
                getSelectionModel().getSelectedItem();
                if (record != null) {
                    onLoadViewOffenseModal(record);
                }
        });
    }

    /**
     * Handles row click to open edit modal.
     */
    @FXML
    void onLoadJuniorHS() {
        currentdDepartment = Department.JHS;
        loadRecordsOfViolation();
        departmentLabel.setText("Junior HS Violations");
        refreshRecord();
    }

    /**
     * Loads Senior High School violations.
     */
    @FXML
    void onLoadSeniorHS() {
        currentdDepartment = Department.SHS;
        loadRecordsOfViolation();
        departmentLabel.setText("Senior HS Violations");
    }

    /**
     * Loads College violations.
     */
    @FXML
    void onLoadCollege() {
        currentdDepartment = Department.COLLEGE;
        loadRecordsOfViolation();
        departmentLabel.setText("College Violations");
    }

    /**
     * Handles action for search button.
     */
    @FXML
    void searchStudent() {
        searchForStudentViolation();
    }

    /**
     * Refreshes the violation table.
     */
    public void refreshRecord() {
        currentdDepartment = Department.JHS;
        loadRecordsOfViolation();
    }
}
