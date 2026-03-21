package org.rocs.osd.model.request;

/**
 * Represents a request made by an employee in
 * the Office of Student Discipline System.
 *
 * Stores details such as the employee making the request,
 * request type, message, and current status.
 */
public class Request {

    /** Unique identifier for the request. */
    private long requestID;

    /** ID of the employee who made the request. */
    private String employeeID;

    /** Short details about the request. */
    private String details;

    /** Message or explanation for the request. */
    private String message;

    /** Type or category of the request. */
    private String type;

    /** Current status of the request. */
    private RequestStatus status;

    /** Default constructor initializing an empty Request object. */
    public Request() {
    }

    /**
     * Constructor to create a Request with all values.
     *
     * @param requestID unique identifier of the request.
     * @param employeeID ID of the employee making the request.
     * @param details short details about the request.
     * @param message message or explanation for the request.
     * @param type type or category of the request.
     * @param status current status of the request.
     */
    public Request(long requestID, String employeeID, String details,
                   String message, String type, RequestStatus status) {
        this.requestID = requestID;
        this.employeeID = employeeID;
        this.details = details;
        this.message = message;
        this.type = type;
        this.status = status;
    }

    /** @return unique identifier of the request */
    public long getRequestID() {
        return requestID;
    }

    /** @param requestID sets the unique identifier of the request */
    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    /** @return ID of the employee making the request */
    public String getEmployeeID() {
        return employeeID;
    }

    /** @param employeeID sets the ID of the employee making the request */
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    /** @return short details about the request */
    public String getDetails() {
        return details;
    }

    /** @param details sets short details about the request */
    public void setDetails(String details) {
        this.details = details;
    }

    /** @return message or explanation of the request */
    public String getMessage() {
        return message;
    }

    /** @param message sets the message or explanation of the request */
    public void setMessage(String message) {
        this.message = message;
    }

    /** @return type or category of the request */
    public String getType() {
        return type;
    }

    /** @param type sets the type or category of the request */
    public void setType(String type) {
        this.type = type;
    }

    /** @return current status of the request */
    public RequestStatus getStatus() {
        return status;
    }

    /** @param status sets the current status of the request */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}