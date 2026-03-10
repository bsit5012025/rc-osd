package org.rocs.osd.data.dao.request;

import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

public interface RequestDao {
    void setRequest(Employee employeeID, String details, String message, String type);
    List<Request> getAllRequests();
    boolean updateRequestStatus(long requestID, RequestStatus status);
}
