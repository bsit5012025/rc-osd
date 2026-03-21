package org.rocs.osd.facade.request;

import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

/**
 * Facade interface for managing requests in the
 * Office of Student Discipline.
 *
 * Provides methods to add requests, retrieve all
 * requests, and update request status.
 */
public interface RequestFacade {

    /**
     * Adds a new request after validating input fields.
     *
     * @param employeeID the ID of the employee submitting the request.
     * @param details brief details of the request.
     * @param message optional message or description.
     * @param type the type of request.
     * @return true if the request was successfully added.
     */
    boolean addRequest(String employeeID, String details,
                       String message, String type);

    /**
     * Retrieves all requests stored in the system.
     *
     * @return a list of all Request objects.
     */
    List<Request> getAllRequest();

    /**
     * Updates the status of an existing request.
     *
     * @param requestID the ID of the request to update.
     * @param status the new status value.
     * @return true if the status was updated successfully.
     */
    boolean updateRequestStatus(long requestID, RequestStatus status);
}
