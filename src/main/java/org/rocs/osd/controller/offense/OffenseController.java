package org.rocs.osd.controller.offense;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controller for managing offenses in the Office of Student Discipline System.
 * This class handles opening the "Add Offense" modal.
 */
public class OffenseController {

    /**
     * Opens the "Add Offense" modal as an undecorated, non-resizable window.
     * @param event the ActionEvent triggered by the user.
     */
    public void onLoadOffenseModal(ActionEvent event){
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/view/offense/addOffenseModal.fxml"));
            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setResizable(false);
            modalStage.setScene(new Scene(root));
            modalStage.show();

        } catch (IOException e) {
            System.err.println("UI Error: Could not find or load AddOffenseModal.fxml. Check the file path and Controller names.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected Error while opening modal: " + e.getMessage());
        }
    }

}
