package org.rocs.osd.controller.request;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
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

import java.util.List;

/**
 * Main controller for the Request module.
 * Handles the logic for displaying the list of request cards.
 */
public class RequestController {

    /** Container where Request Cards will be added. */
    @FXML
    private VBox listContainer;

    /** Facade for handling request operations. */
    private RequestFacade requestFacade;

    /** Facade for handling employee operations. */
    private EmployeeFacade employeeFacade;

    /** Initializes the request view and populates the list. */
    @FXML
    public void initialize() {
        RequestDao requestDao = new RequestDaoImpl();
        requestFacade = new RequestFacadeImpl(requestDao);

        EmployeeDao employeeDao = new EmployeeDaoImpl();
        employeeFacade = new EmployeeFacadeImpl(employeeDao);

        loadRequestData();
    }

    /** Loads and filters request data to create UI cards. */
    private void loadRequestData() {
        if (listContainer == null) {
            return;
        }

        listContainer.getChildren().clear();
        List<Request> requestList = requestFacade.getAllRequest();

        for (Request request : requestList) {
            if (request.getStatus() == RequestStatus.PENDING) {
                Employee employee = employeeFacade
                        .getEmployeeByEmployeeID(request.getEmployeeID());
                String dept = String.valueOf(employee.getDepartment());
                String name = employee.getFirstName() + " "
                        + employee.getMiddleName() + ". "
                        + employee.getLastName();
                String type = request.getType();
                String message = request.getMessage();
                long requestId = request.getRequestID();

                addRequestCard(dept, name, type, message, requestId);
            }
        }
    }

    /**
     * Loads the RequestCard FXML and adds it to the list.
     * @param dept    the department name
     * @param name      the name of the requester
     * @param type      the type of request
     * @param reason    the reason for the request
     * @param requestId the unique identifier for the request
     */
    private void addRequestCard(String dept, String name,
                                String type, String reason, long requestId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/view/request/RequestCard.fxml"));
            VBox card = loader.load();

            RequestCardController controller = loader.getController();
            if (controller != null) {
                controller.setData(dept, name, type, reason, requestId);
                listContainer.getChildren().add(card);
            }
        } catch (Exception e) {
            System.err.println("Error creating request card.");
            e.printStackTrace();
        }
    }
}
