package org.rocs.osd.controller.appeal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;

import java.io.IOException;
import java.util.List;

/**
 * Controller for managing and displaying appeal records
 * in the Office of Student Discipline System.
 * It loads appeals from the database and displays them
 * in the UI.
 */
public class AppealController {

    /**
     * Container that holds all appeal cards in the UI.
     */
    @FXML
    private VBox listContainer;

    /**
     * Facade used to retrieve appeal data from backend.
     */
    private AppealFacade appealFacade = new AppealFacadeImpl();

    /**
     * Initializes the controller.
     * Clears the list and loads appeals from the database.
     */
    @FXML
    public void initialize() {
        if (listContainer != null) {
            listContainer.getChildren().clear();
            loadAppealsFromDB();
        }
    }

    /**
     * Fetches pending appeals from the database
     * and injects them into the listContainer.
     */
    private void loadAppealsFromDB() {
        List<Appeal> appeals = appealFacade.getPendingAppeals();

        for (Appeal appeal : appeals) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/view/appeal/AppealCard.fxml"));
                VBox card = loader.load();

                AppealCardController controller = loader.getController();
                if (controller != null) {
                    controller.setAppeal(appeal);
                    controller.setOnActionComplete(() ->
                            listContainer.getChildren().remove(card));
                    listContainer.getChildren().add(card);
                }
            } catch (IOException e) {
                System.err.println(
                        "Error loading Appeal Card: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
