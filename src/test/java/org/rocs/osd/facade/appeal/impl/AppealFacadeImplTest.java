package org.rocs.osd.facade.appeal.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.person.student.Student;
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
    void testGetAppealsByStatus() {
        Appeal appeal = new Appeal();
        appeal.setAppealID(1L);
        appeal.setMessage("Test appeal");
        appeal.setStatus("PENDING");
        appeal.setRemarks("Test Remarks");

        Record record = new Record();
        record.setRecordId(1L);
        record.setRemarks("Late Submission");
        appeal.setRecord(record);

        Student student = new Student();
        student.setStudentId("S001");
        student.setFirstName("John");
        student.setLastName("Doe");

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(1L);
        enrollment.setStudent(student);
        appeal.setEnrollment(enrollment);

        when(mockDao.findAppealsByStatus("PENDING")).thenReturn(List.of(appeal));

        List<Appeal> result = facade.getAppealsByStatus("PENDING");

        assertNotNull(result);
        assertEquals(1, result.size());
        Appeal actual = result.get(0);
        assertEquals(1L, actual.getAppealID());
        assertEquals("Test appeal", actual.getMessage());
        assertEquals("PENDING", actual.getStatus());
        assertEquals("Test Remarks", result.get(0).getRemarks());

        assertNotNull(actual.getRecord());
        assertEquals(1L, actual.getRecord().getRecordId());
        assertEquals("Late Submission", actual.getRecord().getRemarks());

        assertNotNull(actual.getEnrollment());
        assertEquals(1L, actual.getEnrollment().getEnrollmentId());

        Student actualStudent = actual.getEnrollment().getStudent();
        assertNotNull(actualStudent);
        assertEquals("S001", actualStudent.getStudentId());
        assertEquals("John", actualStudent.getFirstName());
        assertEquals("Doe", actualStudent.getLastName());
        assertEquals(1, result.size());

        verify(mockDao).findAppealsByStatus("PENDING");
    }

    @Test
    void testApproveAppealWithRemarks() {
        assertDoesNotThrow(() -> facade.approveAppeal(1L, "Approved note"));
        verify(mockDao).processAppeal(1L, "APPROVED", "Approved note");
    }

    @Test
    void testApproveAppealWithoutRemarks() {
        assertDoesNotThrow(() -> facade.approveAppeal(1L, null));
        verify(mockDao).processAppeal(1L, "APPROVED", null);
    }

    @Test
    void testDenyAppealWithRemarks() {
        assertDoesNotThrow(() -> facade.denyAppeal(1L, "Invalid request"));
        verify(mockDao).processAppeal(1L, "DENIED", "Invalid request");
    }

    @Test
    void testDenyAppealWithoutRemarksThrowsException() {
        assertDoesNotThrow(() -> assertThrows(IllegalArgumentException.class, () ->
                facade.denyAppeal(1L, " ")
        ));
        verify(mockDao, never()).processAppeal(anyLong(), anyString(), any());
    }
}
