package org.rocs.osd.facade.request.impl;

import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

public class RequestFacadeImpl implements RequestFacade
{
    private RequestDao requestDao;

    private RequestFacadeImpl(RequestDao requestDao)
    {
        this.requestDao = requestDao;
    }

    @Override
    public boolean addRequest(String employeeID, String details, String message, String type)
    {
        if(message != null && message.length() > 500)
        {
            return false;
        }
        if(details == null || details.trim().isEmpty() || details.length() > 100 )
        {
            return false;
        }
        if(type == null || type.trim().isEmpty() || type.length() > 100)
        {
            return false;
        }
        if(employeeID == null || employeeID.trim().isEmpty() || employeeID.length() > 10)
        {
            return false;
        }

        requestDao.addRequest(employeeID, details, message, type);
        return true;
    }

    @Override
    public List<Request> getAllRequest()
    {
        return requestDao.findAllRequests();
    }

    @Override
    public boolean updateRequestStatus(long requestID, RequestStatus status)
    {
        if(status == null)
        {
            return false;
        }

        return requestDao.updateRequestStatus(requestID, status);
    }
}
