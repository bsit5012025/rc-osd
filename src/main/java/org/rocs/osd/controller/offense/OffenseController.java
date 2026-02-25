package org.rocs.osd.controller.offense;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OffenseController {

    public void onLoadOffenseModal(ActionEvent event){
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/view/offense/addOffenseModal.fxml"));
            Stage modalStage = new Stage();
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setResizable(false);
            modalStage.setScene(new Scene(root));
            modalStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
