package org.rocs.osd.facade.appeal.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppealFacadeImplTest {

    @Mock
    private AppealDao mockDao;

    private AppealFacadeImpl facade;

    @BeforeEach
    void setUp() {
        facade = new AppealFacadeImpl();
        try {
            java.lang.reflect.Field daoField = AppealFacadeImpl.class.getDeclaredField("appealDao");
            daoField.setAccessible(true);
            daoField.set(facade, mockDao);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllAppeals() {
        Appeal appeal1 = new Appeal();
        appeal1.setAppealID(1L);
        appeal1.setEnrollmentID(101L);
        appeal1.setStatus("PENDING");

        Appeal appeal2 = new Appeal();
        appeal2.setAppealID(2L);
        appeal2.setEnrollmentID(102L);
        appeal2.setStatus("PENDING");

        when(mockDao.findAllAppealDetails()).thenReturn(Arrays.asList(appeal1, appeal2));

        List<Appeal> result = facade.getAllAppeals();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mockDao, times(1)).findAllAppealDetails();
    }

    @Test
    void testApproveAppeal() {
        Long appealId = 101L;
        assertDoesNotThrow(() -> facade.approveAppeal(appealId));
        verify(mockDao, times(1)).updateAppealStatus(appealId, "APPROVED");
    }

    @Test
    void testRejectAppeal() {
        Long appealId = 102L;
        assertDoesNotThrow(() -> facade.rejectAppeal(appealId));
        verify(mockDao, times(1)).updateAppealStatus(appealId, "REJECTED");
    }
}