package org.rocs.osd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OfficeOfStudentDisciplineApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/offense/editOffenseModal.fxml"));
        Parent mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);Edi
        stage.setScene(scene);
        stage.setTitle("Office of Student Discipline");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
