package org.rocs.osd.facade.appeal.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.enrollment.Enrollment;

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
        appeal.setAppealID(1L);
        appeal.setMessage("Test appeal");
        appeal.setStatus("PENDING");
        appeal.setStudentFullName("John Doe");

        Record record = new Record();
        record.setRecordId(1L);
        record.setRemarks("Late Submission");
        appeal.setRecord(record);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(1L);
        enrollment.setStudentId("S001");
        appeal.setEnrollment(enrollment);

        when(mockDao.findPendingAppealsWithDetails()).thenReturn(List.of(appeal));

        List<Appeal> result = facade.getPendingAppeals();

        assertNotNull(result);
        assertEquals(1, result.size());
        Appeal actual = result.get(0);
        assertEquals(1L, actual.getAppealID());
        assertEquals("Test appeal", actual.getMessage());
        assertEquals("PENDING", actual.getStatus());
        assertEquals("John Doe", actual.getStudentFullName());

        assertNotNull(actual.getRecord());
        assertEquals(1L, actual.getRecord().getRecordId());
        assertEquals("Late Submission", actual.getRecord().getRemarks());

        assertNotNull(actual.getEnrollment());
        assertEquals(1L, actual.getEnrollment().getEnrollmentId());
        assertEquals("S001", actual.getEnrollment().getStudentId());

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