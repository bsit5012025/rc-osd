package org.rocs.osd.model.request;

import org.rocs.osd.model.person.employee.Employee;

public class Request {

        private long requestID;
        private Employee employee ;
        private String details;
        private String message;
        private String type;
        private RequestStatus status;

    public Request(){

    }

    public Request(long requestID, Employee employee, String details, String message, String type, RequestStatus status) {
        this.requestID = requestID;
        this.employee = employee;
        this.details = details;
        this.message = message;
        this.type = type;
        this.status = status;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

}
