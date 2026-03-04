package org.rocs.osd.facade.appeal.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppealFacadeImplTest {

    @Mock
    private AppealDao appealDao;

    private AppealFacadeImpl facade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        facade = new AppealFacadeImpl(appealDao);
    }

    @Test
    void testFileAppeal() {

        Appeal appeal = new Appeal(1L, 1L, "Test Message", "OLD_STATUS");

        when(appealDao.saveAppeal(any(Appeal.class))).thenReturn(appeal);

        Appeal result = facade.fileAppeal(appeal);

        assertEquals("PENDING", result.getAppealStatus());
        verify(appealDao, times(1)).saveAppeal(appeal);
    }

    @Test
    void testGetAppealById() {

        Appeal appeal = new Appeal();
        when(appealDao.findByAppealId(1L)).thenReturn(appeal);

        Appeal result = facade.getAppealById(1L);

        assertNotNull(result);
        verify(appealDao, times(1)).findByAppealId(1L);
    }

    @Test
    void testApproveAppeal() {

        facade.approveAppeal(1L);

        verify(appealDao, times(1)).updateAppealStatus(1L, "APPROVED");
    }

    @Test
    void testRejectAppeal() {

        facade.rejectAppeal(1L);

        verify(appealDao, times(1)).updateAppealStatus(1L, "REJECTED");
    }

    @Test
    void testGetAppealsByEnrollmentId() {

        when(appealDao.findByEnrollmentId(1L)).thenReturn(Collections.emptyList());

        assertNotNull(facade.getAppealsByEnrollmentId(1L));

        verify(appealDao, times(1)).findByEnrollmentId(1L);
    }
}
