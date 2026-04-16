package org.rocs.osd.controller.appeal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import org.rocs.osd.facade.appeal.AppealFacade;
import org.rocs.osd.facade.appeal.impl.AppealFacadeImpl;
import org.rocs.osd.model.appeal.Appeal;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

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
     * Initializes the controller.
     * Clears the list and loads appeals from the database.
     */
    @FXML
    public void initialize() {
        loadAppealsByStatus("PENDING");
    }

    /**
     * Facade used to retrieve appeal data from backend.
     */
    private AppealFacade appealFacade;

    /**
     * Static mock for approve dialog (for testing).
     */
    private static Consumer<Runnable> staticMockApproveDialog;

    /**
     * Static mock for deny dialog (for testing).
     */
    private static Consumer<Runnable> staticMockDenyDialog;

    /**
     * Sets the appeal facade for dependency injection.
     * Used for testing to inject mock facades.
     *
     * @param pAppealFacade the facade to use
     */
    public void setAppealFacade(AppealFacade pAppealFacade) {
        this.appealFacade = pAppealFacade;
    }

    /**
     * Gets the appeal facade, creating default implementation if not set.
     *
     * @return the appeal facade
     */
    public AppealFacade getAppealFacade() {
        if (appealFacade == null) {
            appealFacade = new AppealFacadeImpl();
        }
        return appealFacade;
    }

    /**
     * Sets static mock approve dialog for testing.
     *
     * @param pCallback the callback to use
     */
    public static void setStaticMockApproveDialog(Consumer<Runnable> pCallback) {
        staticMockApproveDialog = pCallback;
    }

    /**
     * Sets static mock deny dialog for testing.
     *
     * @param pCallback the callback to use
     */
    public static void setStaticMockDenyDialog(Consumer<Runnable> pCallback) {
        staticMockDenyDialog = pCallback;
    }

    /**
     * Clears static mock dialogs.
     */
    public static void clearStaticMockDialogs() {
        staticMockApproveDialog = null;
        staticMockDenyDialog = null;
    }

    /**
     * Gets the static mock approve dialog.
     *
     * @return the mock dialog
     */
    public static Consumer<Runnable> getStaticMockApproveDialog() {
        return staticMockApproveDialog;
    }

    /**
     * Gets the static mock deny dialog.
     *
     * @return the mock dialog
     */
    public static Consumer<Runnable> getStaticMockDenyDialog() {
        return staticMockDenyDialog;
    }

    /**
     * Fetches pending appeals from the database
     * and injects them into the listContainer.
     * @param status load appeal status.
     */
    private void loadAppealsByStatus(String status) {

        if (listContainer == null) {
            return;
        }

        listContainer.getChildren().clear();

        List<Appeal> appeals = getAppealFacade().getAppealsByStatus(status);

        if (appeals == null) {
            return;
        }

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
                    AppealCardController controller =
                            loader.getController();

                    controller.setAppealFacade(getAppealFacade());

                    controller.setAppeal(appeal);
                    controller.setStatus(status);

                    controller.setOnActionComplete(() ->
                            loadAppealsByStatus("PENDING"));

                    controller.setShowApproveDialog(staticMockApproveDialog);
                    controller.setShowDenyDialog(staticMockDenyDialog);

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



    /**
     * Handles pending tab click.
     */
    @FXML
    void handlePendingTab() {
        loadAppealsByStatus("PENDING");
    }

    /**
     * Handles approved tab click.
     */
    @FXML
    void handleApprovedTab() {
        loadAppealsByStatus("APPROVED");
    }

    /**
     * Handles denied tab click.
     */
    @FXML
    void handleDeniedTab() {
        loadAppealsByStatus("DENIED");
    }
}