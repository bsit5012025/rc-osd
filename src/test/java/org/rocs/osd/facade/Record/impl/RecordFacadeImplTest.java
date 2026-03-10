package org.rocs.osd.facade.Record.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.Record.RecordFacade;
import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.sql.Date;

@ExtendWith(MockitoExtension.class)
class RecordFacadeImplTest
{
    @Mock
    private RecordDao recordDao;

    private Record record;

    private RecordFacade recordFacade;

    @BeforeEach
    public void setUp() {
      recordFacade = new RecordFacadeImpl(recordDao);
    }

        @Test
        void testCreateStudentRecord () {

            when(recordDao.addStudentRecord(
                    anyLong(),
                    anyString(),
                    anyLong(),
                    any(Date.class),
                    anyLong(),
                    nullable(String.class),
                    eq(RecordStatus.PENDING)
            )).thenReturn(true);

            boolean result = recordFacade.createStudentRecord(
                    1L,
                    "EMP-001",
                    2L,
                    Date.valueOf("2025-03-08"),
                    3L,
                    "Bullying incident"
            );

            assertTrue(result);

            verify(recordDao, times(1)).addStudentRecord(
                    1L,
                    "EMP-001",
                    2L,
                    Date.valueOf("2025-03-08"),
                    3L,
                    "Bullying incident",
                    RecordStatus.PENDING
            );
        }

    @Test
    public void testUpdateStudentRecord()
    {
        when(recordDao.updateRecord(any(Record.class))).thenReturn(true);

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(Long.valueOf(1));

        Employee employee = new Employee();
        employee.setEmployeeId("EMP-002");

        Offense offense = new Offense();
        offense.setOffenseId(Long.valueOf(1));

        DisciplinaryAction action = new DisciplinaryAction();
        action.setActionId(Long.valueOf(1));

        boolean result = recordFacade.updateStudentRecord(enrollment,
                employee, offense,  Date.valueOf("2024-09-15"),
                action, "Student caught vaping in school",
                RecordStatus.PENDING);

        assertTrue(result);
        verify(recordDao, times(1)).updateRecord(any(Record.class));
    }
}