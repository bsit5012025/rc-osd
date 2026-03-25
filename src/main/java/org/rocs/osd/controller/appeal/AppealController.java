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
        loadAppealsByStatus("PENDING");
    }

    /**
     * Fetches pending appeals from the database
     * and injects them into the listContainer.
     * @param status load appeal status.
     */
    private void loadAppealsByStatus(String status) {

        if (listContainer == null) return; {

            listContainer.getChildren().clear();

            List<Appeal> appeals = appealFacade.getAppealsByStatus(status);

            if (appeals == null) return; {

            for (Appeal appeal : appeals) {
                try {

                    FXMLLoader loader;

                    if ("APPROVED".equals(status)) {
                        loader = new FXMLLoader(getClass().getResource(
                                "/view/appeal/approvedAppealCard.fxml"));
                    } else if ("DENIED".equals(status)) {
                        loader = new FXMLLoader(getClass().getResource(
                                "/view/appeal/deniedAppealCard.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource(
                                "/view/appeal/appealCard.fxml"));
                    }

                    VBox card = loader.load();

                    if ("PENDING".equals(status)) {
                        AppealCardController controller = loader.getController();

                        controller.setAppeal(appeal);

                        controller.setStatus(status);

                        controller.setOnActionComplete(() ->
                                loadAppealsByStatus("PENDING"));

                    } else if ("APPROVED".equals(status)) {
                        ApprovedAppealCardController controller =
                                loader.getController();
                        controller.setAppeal(appeal);

                    } else {
                        DeniedAppealCardController controller =
                                loader.getController();
                        controller.setAppeal(appeal);
                    }

                    listContainer.getChildren().add(card);

                } catch (IOException e) {
                    System.err.println("Error loading Appeal Card: "
                            + e.getMessage());
                    e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Handles pending tab click.
     */
    @FXML
    private void handlePendingTab() {
        loadAppealsByStatus("PENDING");
    }

    /**
     * Handles approved tab click.
     */
    @FXML
    private void handleApprovedTab() {
        loadAppealsByStatus("APPROVED");
    }

    /**
     * Handles denied tab click.
     */
    @FXML
    private void handleDeniedTab() {
        loadAppealsByStatus("DENIED");
    }
}
