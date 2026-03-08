package org.rocs.osd.facade.appeal.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppealFacadeImplTest {

    @Mock private AppealDao mockDao;
    private AppealFacadeImpl facade;

    @BeforeEach
    void setUp() {
        facade = new AppealFacadeImpl(mockDao);
    }

    @Test
    void testGetPendingAppeals() {
        Appeal appeal = new Appeal();
        Student student = new Student();
        Offense offense = new Offense();
        when(mockDao.findPendingAppealsWithDetails()).thenReturn(List.<Object[]>of(new Object[]{appeal, student, offense}));

        List<Object[]> result = facade.getPendingAppeals();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(appeal, result.get(0)[0]);
        assertSame(student, result.get(0)[1]);
        assertSame(offense, result.get(0)[2]);
        verify(mockDao).findPendingAppealsWithDetails();
    }

    @Test
    void testApproveAppeal() {
        assertDoesNotThrow(() -> facade.approveAppeal(1L));
        verify(mockDao).updateAppealStatus(1L, "APPROVED");
    }

    @Test
    void testDeniedAppeal() {
        assertDoesNotThrow(() -> facade.deniedAppeal(1L));
        verify(mockDao).updateAppealStatus(1L, "DENIED");
    }
}