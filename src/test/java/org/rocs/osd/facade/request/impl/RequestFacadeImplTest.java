package org.rocs.osd.facade.request.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestFacadeImplTest
{
    @Mock
    private RequestDao requestDao;

    private RequestFacade requestFacade;

    @BeforeEach
    public void setUp() {
        requestFacade = new RequestFacadeImpl(requestDao);
    }

    @Test
    void testAddRequest()
    {
        when(requestDao.addRequest(anyString(),anyString(),nullable(String.class),anyString())).thenReturn(true);

        boolean status = requestFacade.addRequest(
                "EMP-001",
                "St. Andrew",
                "Requesting for the conduct record of all students in St. Andrew",
                "By Section"
        );

        assertTrue(status);
        verify(requestDao, times(1)).addRequest(
                "EMP-001",
                "St. Andrew",
                "Requesting for the conduct record of all students in St. Andrew",
                "By Section"
        );
    }

    @Test
    void testGetAllRequest()
    {
        Request request = new Request();
        request.setRequestID(1L);
        request.setEmployeeID("EMP-001");
        request.setDetails("St. Andrew");
        request.setType("By Section");
        request.setMessage("Requesting for the conduct record of all students in St. Andrew");
        request.setStatus(RequestStatus.APPROVED);

        when(requestDao.findAllRequests()).thenReturn(List.of(request));

        List<Request> list = requestFacade.getAllRequest();
        Request list1 = list.get(0);

        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(1L ,list1.getRequestID());
        assertEquals("EMP-001" ,list1.getEmployeeID());
        assertEquals("St. Andrew" ,list1.getDetails());
        assertEquals("By Section" ,list1.getType());
        assertEquals("Requesting for the conduct record of all students in St. Andrew" ,list1.getMessage());
        assertEquals(RequestStatus.APPROVED ,list1.getStatus());

        verify(requestDao, times(1)).findAllRequests();
    }

    @Test
    void testUpdateRequestStatus()
    {
        when(requestDao.updateRequestStatus(anyLong(), any(RequestStatus.class))).thenReturn(true);

        boolean status = requestFacade.updateRequestStatus(1L, RequestStatus.APPROVED);

        assertTrue(status);
        verify(requestDao, times(1)).updateRequestStatus(1L, RequestStatus.APPROVED);
    }
}