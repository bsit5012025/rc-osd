package org.rocs.osd.data.dao.request;

import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;
import java.util.List;

/**
 * DAO interface for handling Request entities in the database.
 * This class handles database operations for Request objects including:
 * adding a new request, fetching all requests, and updating request status.
 */
public interface RequestDao {

    /**
     * Adds a new request to the database.
     * @param employeeID the ID of the employee submitting the request.
     * @param details the details describing the request.
     * @param message the message or reason for the request.
     * @param type the category or type of request.
     * @return true if the request was successfully added, false otherwise.
     */
    boolean addRequest(String employeeID,
                       String details,
                       String message,
                       String type);

    /**
     * Retrieves all requests from the database.
     * Each database record is mapped to a Request object.
     *
     * @return a List of Request objects.
     */
    List<Request> findAllRequests();

    /**
     * Updates the status of a request in the database.
     * @param requestID the unique ID of the request to update.
     * @param status    the new status to assign to the request.
     * @return true if the update was successful, false otherwise.
     */
    boolean updateRequestStatus(long requestID, RequestStatus status);
}
