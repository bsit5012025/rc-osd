package org.rocs.osd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point for the Office of
 * Student Discipline application.
 *
 * Launches the JavaFX GUI and loads
 * the login screen as the initial scene.
 */
public class OfficeOfStudentDisciplineApplication extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for this application.
     * @throws Exception if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass()
        .getResource("/view/login/login.fxml"));
        Parent mainLayout = loader.load();


        Scene scene = new Scene(mainLayout);


        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Office of Student Discipline");
        stage.show();
    }

    /**
     * Main method to launch the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(); // launch JavaFX application
    }
}