package org.rocs.osd;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class OfficeOfStudentDisciplineApplication extends Application{

        public OfficeOfStudentDisciplineApplication() {
        }
    // TODO: to be removed. for testing purposes only
        @Override
        public void start(Stage stage) {
            Label label = new Label("Test JavaFX is running");

            StackPane root = new StackPane(label);
            Scene scene = new Scene(root, 400, 200);

            stage.setTitle("Test");
            stage.setScene(scene);
            stage.show();
        }
}