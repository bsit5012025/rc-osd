package org.rocs.osd.facade.request;

import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

public interface RequestFacade
{
    boolean addRequest(String employeeID, String details, String message, String type);
    List<Request> getAllRequest();
    boolean updateRequestStatus(long requestID, RequestStatus status);
}
