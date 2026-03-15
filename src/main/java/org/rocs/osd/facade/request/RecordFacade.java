package org.rocs.osd.facade.request;

import org.rocs.osd.model.request.Request;

import java.util.List;

public interface RecordFacade
{
    boolean addRequest(String employeeID, String details, String message, String type);
    List<Request> getAllRequest();
    boolean updateRequestStatus();
}
