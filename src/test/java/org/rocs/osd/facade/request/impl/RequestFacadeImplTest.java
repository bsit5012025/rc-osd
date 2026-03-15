package org.rocs.osd.facade.request.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.facade.request.RequestFacade;
import org.rocs.osd.model.request.Request;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestFacadeImplTest
{
    @Mock
    private RequestDao requestDao;

    private Request request;

    private RequestFacade requestFacade;

    @BeforeEach
    public void setUp() {
        requestFacade = new RequestFacadeImpl(requestDao);
    }

    @Test
    void testAddRequest()
    {
        when(requestDao.addRequest(anyString(),anyString(),anyString(),anyString())).thenReturn(true);

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

    }

    @Test
    void testUpdateRequestStatus()
    {

    }
}