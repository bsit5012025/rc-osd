package org.rocs.osd.controller.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.rocs.osd.data.dao.enrollment.impl.EnrollmentDaoImpl;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.facade.enrollment.impl.EnrollmentFacadeImpl;
import org.rocs.osd.model.enrollment.Enrollment;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Controller for managing student records in the table view.
 * Handles loading student enrollment data and displaying it.
 */
public class StudentController {

    /** Table view displaying student enrollments. */
    @FXML
    private TableView<Enrollment> studentTable;

    /** Column for student ID. */
    @FXML
    private TableColumn<Enrollment, String> studentIdColumn;

    /** Column for student full name. */
    @FXML
    private TableColumn<Enrollment, String> studentNameColumn;

    /** Column for grade level or course. */
    @FXML
    private TableColumn<Enrollment, String> gradeCourseColumn;

    /** Column for section. */
    @FXML
    private TableColumn<Enrollment, String> sectionColumn;

    /** Search field for filtering students. */
    @FXML
    private TextField searchField;

    /** Facade used to retrieve enrollment data. */
    private EnrollmentFacade enrollmentFacade;

    /** Static controller factory for nested FXML loading. */
    private static Callback<Class<?>, Object> controllerFactory;

    /**
     * Sets the static controller factory for nested FXML loading.
     *
     * @param factory the controller factory callback
     */
    public static void setControllerFactory(
            Callback<Class<?>, Object> factory) {
        controllerFactory = factory;
    }

    /**
     * Clears the static controller factory.
     */
    public static void clearControllerFactory() {
        controllerFactory = null;
    }

    /**
     * Sets the enrollment facade for dependency injection.
     *
     * @param facade the facade to use
     */
    public void setEnrollmentFacade(EnrollmentFacade facade) {
        this.enrollmentFacade = facade;
    }

    /**
     * Initializes the controller.
     * Instantiates the facade and loads table data.
     */
    @FXML
    public void initialize() {
        if (enrollmentFacade == null) {
            enrollmentFacade = new EnrollmentFacadeImpl(
                    new EnrollmentDaoImpl()
            );
        }
        if (searchField != null) {
            searchField.setOnAction(e -> onSearch());
        }
        wireSortHandlers();
        loadDataToTable();
        selectStudentRecord();
    }

    /**
     * Wires sort menu item handlers by looking up the MenuButton
     * in the scene graph.
     */
    private void wireSortHandlers() {
        if (studentTable == null) {
            return;
        }
        studentTable.sceneProperty().addListener(
                (obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        doWireSortHandlers(newScene);
                    }
                });
        if (studentTable.getScene() != null) {
            doWireSortHandlers(studentTable.getScene());
        }
    }

    private void doWireSortHandlers(Scene scene) {
        scene.getRoot().lookupAll(".menu-button").stream()
                .filter(node -> node instanceof MenuButton)
                .map(node -> (MenuButton) node)
                .filter(mb -> "Sort By".equals(mb.getText()))
                .findFirst()
                .ifPresent(mb -> {
                    for (MenuItem item : mb.getItems()) {
                        String text = item.getText();
                        if ("Name (A-Z)".equals(text)) {
                            item.setOnAction(e -> onSortAZ());
                        } else if ("Name (Z-A)".equals(text)) {
                            item.setOnAction(e -> onSortZA());
                        } else if ("Year Level (Ascending)".equals(text)) {
                            item.setOnAction(e -> onSortYearAscending());
                        }
                    }
                });
    }

    /**
     * Handles search action.
     */
    @FXML
    private void onSearch() {
        String query = searchField.getText().trim()
                .toLowerCase(Locale.ROOT);
        if (query.isEmpty()) {
            loadDataToTable();
            return;
        }
        List<Enrollment> all = enrollmentFacade
                .getAllLatestEnrollments();
        List<Enrollment> filtered = all.stream()
                .filter(e -> {
                    String name = e.getStudent().getFirstName()
                            + " " + e.getStudent().getLastName();
                    return name.toLowerCase(Locale.ROOT)
                            .contains(query);
                })
                .collect(Collectors.toList());
        studentTable.setItems(FXCollections
                .observableArrayList(filtered));
    }

    /**
     * Sorts table by name A-Z.
     */
    @FXML
    private void onSortAZ() {
        List<Enrollment> sorted = studentTable.getItems()
                .stream()
                .sorted(Comparator.comparing(e -> e.getStudent()
                        .getFirstName()))
                .collect(Collectors.toList());
        studentTable.setItems(FXCollections
                .observableArrayList(sorted));
    }

    /**
     * Sorts table by name Z-A.
     */
    @FXML
    private void onSortZA() {
        List<Enrollment> sorted = studentTable.getItems()
                .stream()
                .sorted(Comparator.comparing(
                                (Enrollment e) -> e.getStudent()
                                        .getFirstName())
                        .reversed())
                .collect(Collectors.toList());
        studentTable.setItems(FXCollections
                .observableArrayList(sorted));
    }

    /**
     * Sorts table by year level ascending.
     */
    @FXML
    private void onSortYearAscending() {
        List<Enrollment> sorted = studentTable.getItems()
                .stream()
                .sorted(Comparator.comparing(
                        Enrollment::getStudentLevel))
                .collect(Collectors.toList());
        studentTable.setItems(FXCollections
                .observableArrayList(sorted));
    }

    /**
     * A method to update the table view
     * and its contents.
     */
    public void refreshTable() {
        loadDataToTable();
        selectStudentRecord();
    }

    /**
     * Loads student data into the table.
     * Sets cell factories and populates the table.
     */
    public void loadDataToTable() {

        studentIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue()
                                .getStudent()
                                .getStudentId()
                )
        );

        // Displays student full name
        studentNameColumn.setCellValueFactory(cellData -> {
            var student = cellData.getValue().getStudent();
            return new SimpleStringProperty(
                    student.getFirstName()
                            + " "
                            + student.getLastName()
            );
        });

        gradeCourseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getStudentLevel()
                )
        );

        sectionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getSection()
                )
        );

        studentTable.setItems(
               FXCollections.observableArrayList(
                        enrollmentFacade.getAllLatestEnrollments()
               )
        );
    }

    /**
     * Allows clicking only on rows of the table view,
     * Ignores clicks on empty areas and column titles.
     */
    private void selectStudentRecord() {
        studentTable.setRowFactory(wholeRow -> {
            TableRow<Enrollment> row = new TableRow<>();
            row.setOnMouseClicked(evenClickOnRow -> {
                if (!row.isEmpty()) {
                    Enrollment enrollment = studentTable.
                            getSelectionModel().getSelectedItem();
                    loadStudentRecord(enrollment);
                }
            });
            return row;
        });
    }

    /**
     * Opens the student Record with selected info
     * of the student.
     *
     * @param enrollment student info to be displayed.
     */
    private void loadStudentRecord(Enrollment enrollment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/student/studentRecord.fxml"));
            if (controllerFactory != null) {
                loader.setControllerFactory(controllerFactory);
            }
            Parent root = loader.load();

            StudentRecordController studentRecordController
                    = loader.getController();
            studentRecordController.setStudentData(enrollment);
            studentRecordController.setStudentController(this);

            Stage studentStage = new Stage();

            studentStage.initModality(Modality.APPLICATION_MODAL);
            studentStage.initStyle(StageStyle.UNDECORATED);
            studentStage.setResizable(false);
            studentStage.setScene(new Scene(root));
            studentStage.centerOnScreen();
            studentStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
