package org.rocs.osd.data.dao.request;

import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

public interface RequestDao {
    void addRequest(String employeeID, String details, String message, String type);
    List<Request> findAllRequests();
    boolean updateRequestStatus(long requestID, RequestStatus status);
}
