package org.rocs.osd.facade.request.impl;

import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

public class RequestFacadeImpl implements RequestFacade
{
    private RequestDao requestDao;

    public RequestFacadeImpl(RequestDao requestDao)
    {
        this.requestDao = requestDao;
    }

    @Override
    public boolean addRequest(String employeeID, String details, String message, String type)
    {
        boolean status = validateString(employeeID,10,false) &&
                validateString(details, 100, false) &&
                validateString(message, 500, true) &&
                validateString(type, 100, false );

        if(status)
        {
            requestDao.addRequest(employeeID, details, message, type);
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean validateString(String s, int maxLength, boolean allowsNull)
    {
        if(allowsNull)
            return (s != null && s.length() > maxLength)? false:true;
        else
            return (s == null || s.isBlank() || s.isEmpty() || s.length() > maxLength)? false:true;
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
