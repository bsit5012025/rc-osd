package org.rocs.osd.controller.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.rocs.osd.controller.request.RequestCardController;
import org.rocs.osd.data.dao.employee.EmployeeDao;
import org.rocs.osd.data.dao.employee.impl.EmployeeDaoImpl;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.data.dao.request.impl.RequestDaoImpl;
import org.rocs.osd.facade.employee.EmployeeFacade;
import org.rocs.osd.facade.employee.impl.EmployeeFacadeImpl;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.facade.request.impl.RequestFacadeImpl;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;


/**
 * Controller for the Dashboard screen of the Office of Student Discipline.
 * Manages module loading, sidebar toggling, and logout/popup actions.
 */
public class DashboardController {

    /** Wrapper for dynamically loading modules inside the dashboard. */
    @FXML
    private StackPane mainContentWrapper;

    /** Logout button in the dashboard UI. */
    @FXML
    private Button logoutButton;

    /** Sidebar container holding navigation buttons. */
    @FXML
    private VBox sidebar;

    /** Flag indicating if the sidebar is currently collapsed. */
    @FXML
    private boolean sidebarCollapsed = false;

    /** Container for request cards in the Request module. */
    @FXML
    private VBox listContainer;

    /** Facade for handling request operations. */
    private RequestFacade requestFacade;

    /** Facade for handling employee operations. */
    private EmployeeFacade employeeFacade;

    /** Initializes the dashboard controller and loads request data. */
    @FXML
    public void initialize() {
        RequestDao requestDao = new RequestDaoImpl();
        requestFacade = new RequestFacadeImpl(requestDao);

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        employeeFacade = new EmployeeFacadeImpl(employeeDao);

        if (listContainer != null) {
            loadRequestData();
        }
    }

    /** Loads sample request data into the list container. */
    private void loadRequestData() {
        if (listContainer == null) {
            return;
        }
        listContainer.getChildren().clear();
        createRequestCards();
    }

    private void createRequestCards() {
        List<Request> requestList = requestFacade.getAllRequest();

        for (Request request : requestList) {
            if (request.getStatus() == RequestStatus.PENDING) {
                Employee employee = employeeFacade.getEmployeeByEmployeeID(
                        request.getEmployeeID());
                String dept = String.valueOf(employee.getDepartment());
                String name = employee.getFirstName()
                        + " " + employee.getMiddleName()
                        + ". " + employee.getLastName();
                String type = request.getType();
                String message = request.getMessage();
                long requestId = request.getRequestID();
                addRequestCard(dept, name, type, message, requestId);
            }
        }
    }

    /**
     * Adds a single request card to the list container.
     * @param dept the department of the request.
     * @param name the student name.
     * @param type the type of request.
     * @param reason the reason for the request.
     * @param requestId To record where card is
     */
    private void addRequestCard(String dept,
                                String name,
                                String type,
                                String reason,
                                long requestId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/request/RequestCard.fxml"));
            VBox card = loader.load();
            RequestCardController controller = loader.getController();
            if (controller != null) {
                controller.setData(dept, name, type, reason, requestId);
                listContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            System.out.println("Error loading Request Tab");
            e.printStackTrace();
        }
    }

    /**
     * Opens the logout confirmation dialog when the logout button is clicked.
     * @param event ActionEvent triggered by clicking the logout button.
     */
    @FXML
    public void onLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/view/dialogs/logoutConfirmation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            Stage currentStage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            stage.initOwner(currentStage);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Toggles the sidebar between collapsed and expanded states. */
    @FXML
    public void toggleSidebar() {
        if (sidebarCollapsed) {
            sidebar.setPrefWidth(200);
            sidebar.setMinWidth(200);
            sidebar.setMaxWidth(200);
            for (Node node : sidebar.lookupAll(".sidebarItem")) {
                if (node instanceof Labeled button) {
                    button.setText(button.getUserData() != null
                            ? button.getUserData().toString()
                            : button.getText());
                }
            }
            sidebarCollapsed = false;
        } else {
            sidebar.setPrefWidth(70);
            sidebar.setMinWidth(70);
            sidebar.setMaxWidth(70);
            for (Node node : sidebar.lookupAll(".sidebarItem")) {
                if (node instanceof Labeled button) {
                    if (button.getUserData() == null) {
                        button.setUserData(button.getText());
                    }
                    button.setText("");
                }
            }
            sidebarCollapsed = true;
        }
    }

    /**
     * Loads the Offense module into the dashboard content area.
     * @param event ActionEvent triggered by the Offense button.
     */

    @FXML
    public void onLoadOffense(ActionEvent event) {
        try {
            Parent offenseView = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/view/offense/offense.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(offenseView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found at the specified path. "
                    + e.getMessage());
        } catch (IOException e) {
            System.err.println(
                    "Failed to load Offense view. Check FXML errors. "
                    + e.getMessage());
        }
    }

    /**
     * Loads the Dashboard (center) module into the content area.
     * @param event ActionEvent triggered by the Dashboard button.
     */
    @FXML
    public void onLoadDashboard(ActionEvent event) {
        try {
            Parent dashboardView = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource(
                    "/view/dashboard/centerDashboard.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(dashboardView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found. " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to load Dashboard view. "
            + e.getMessage());
        }
    }

    /**
     * Loads the Appeal module into the dashboard content area.
     * @param event ActionEvent triggered by the Appeal button.
     */
    @FXML
    public void onLoadAppeal(ActionEvent event) {
        try {
            Parent appealView = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/view/appeal/appeal.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(appealView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found. "
                    + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to load Appeal view. "
                    + e.getMessage());
        }
    }

    /**
     * Loads the Request module into the dashboard content area.
     * @param event ActionEvent triggered by the Request button.
     */
    @FXML
    public void onLoadRequest(ActionEvent event) {
        try {
            Parent requestView = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/view/request/request.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(requestView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found. " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to load Request view. "
                    + e.getMessage());
        }
    }

    /**
     * Loads the Student module into the dashboard content area.
     * @param event ActionEvent triggered by the Student button.
     */
    @FXML
    public void onLoadStudent(ActionEvent event) {
        try {
            Parent studentView = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getResource("/view/student/student.fxml")));
            mainContentWrapper.getChildren().clear();
            mainContentWrapper.getChildren().add(studentView);
        } catch (NullPointerException e) {
            System.err.println("FXML file not found. "
                    + e.getMessage());
        } catch (IOException e) {
            System.err.println("Failed to load Student view. "
                    + e.getMessage());
        }
    }

    /**
     * Logs the user out and redirects to the login screen.
     * @param event ActionEvent triggered on logout confirmation.
     */
    public void logout(ActionEvent event) {
        try {
            Stage popupStage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();
            Stage mainStage = (Stage) popupStage.getOwner();
            popupStage.close();
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/view/login/login.fxml"));
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(),
                    screenBounds.getHeight());
            mainStage.setScene(scene);
            mainStage.setMaximized(true);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the currently opened popup window.
     * @param event ActionEvent triggered by the close button.
     */
    public void closePopup(ActionEvent event) {
        if (event != null && event.getSource() instanceof Node node) {
            Scene scene = node.getScene();
            if (scene != null && scene.getWindow() instanceof Stage stage) {
                stage.close();
            }
        }
    }

}
