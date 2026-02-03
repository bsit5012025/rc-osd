package org.rocs.osd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.rocs.osd.controller.login.LoginController;

public class OfficeOfStudentDisciplineApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login/dashboard.fxml"));
        Parent mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Office of Student Discipline");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
