package org.rocs.osd.model.request;

/**
 * Represents a request submitted by an employee within the Office of Student Discipline System.
 * Each request contains details about the request, its type, and its current status.
 */
public class Request {

        private long requestID;
        private String employeeID;
        private String details;
        private String message;
        private String type;
        private RequestStatus status;

    /**
     * Default constructor.
     * Initializes an empty Request object.
     */
    public Request(){

    }

    /**
     * Constructs a Request object with the specified details.
     *
     * @param requestID  the unique ID of the request
     * @param employeeID the ID of the employee submitting the request
     * @param details    the details or description of the request
     * @param message    an optional message associated with the request
     * @param type       the type or category of the request
     * @param status     the current status of the request
     */
    public Request(long requestID, String employeeID, String details, String message, String type, RequestStatus status) {
        this.requestID = requestID;
        this.employeeID = employeeID;
        this.details = details;
        this.message = message;
        this.type = type;
        this.status = status;
    }

    /**
     * Returns the unique ID of the request.
     * @return request ID.
     */
    public long getRequestID() {
        return requestID;
    }

    /**
     * Sets the unique ID of the request.
     * @param requestID the request ID to set.
     */
    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    /**
     * Returns the employee ID who submitted the request.
     * @return employee ID.
     */
    public String getEmployeeID() {
        return employeeID;
    }
    /**
     * Sets the employee ID of the request submitter.
     * @param employeeID the employee ID to set.
     */
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    /**
     * Returns the details or description of the request.
     * @return request details.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the details or description of the request.
     * @param details the request details to set.
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Returns the optional message associated with the request.
     * @return message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the optional message for the request.
     * @param message the message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the type or category of the request.
     * @return request type.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type or category of the request.
     * @param type the request type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the current status of the request.
     * @return request status.
     */
    public RequestStatus getStatus() {
        return status;
    }
    /**
     * Sets the current status of the request.
     * @param status the status to set.
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

}
