package org.rocs.osd.facade.record.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryaction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        boolean result = recordFacade.updateStudentRecord(1L, enrollment,
                employee, offense,  Date.valueOf("2024-09-15"),
                action, "Student caught vaping in school",
                RecordStatus.PENDING);

        assertTrue(result);
        verify(recordDao, times(1)).updateRecord(any(Record.class));
    }
    @Test
    void testResolveRecord() {

        Record record = new Record();

        when(recordDao.updateRecord(record)).thenReturn(true);

        boolean result = recordFacade.resolveRecord(record);

        assertTrue(result);
        verify(recordDao).updateRecord(record);
    }
    @Test
    void testGetMostFrequentOffense(){

        Map<String, Integer> offenses = new LinkedHashMap<>();
        offenses.put("Bullying", 10);
        offenses.put("Vaping", 5);
        offenses.put("Cheating", 5);

        when(recordDao.findMostFrequentOffenses("2024-2025")).thenReturn(offenses);
        when(recordDao.findTotalViolations("2024-2025")).thenReturn(20);

        Map<String, Double> result = recordFacade.getMostFrequentOffense("2024-2025");

        assertTrue(result.containsKey("Bullying"));
        assertTrue(result.containsKey("Vaping"));
        assertTrue(result.containsKey("Cheating"));
        verify(recordDao).findMostFrequentOffenses("2024-2025");
        verify(recordDao).findTotalViolations("2024-2025");
    }
    @Test
    void testGetTodayViolations(){
        when(recordDao.findTodayViolations()).thenReturn(7);

        int result = recordFacade.getTodayViolations();

        assertEquals(7, result);
        verify(recordDao, times(1)).findTodayViolations();
    }
    @Test
    void testGetTotalViolations(){
        when(recordDao.findTotalViolations("2024-2025")).thenReturn(25);

        int result = recordFacade.getTotalViolations("2024-2025");

        assertEquals(25, result);

        verify(recordDao).findTotalViolations("2024-2025");
    }
    @Test
    void testGetViolationsByDepartment(){
        Department department = Department.COLLEGE;

        List<Record> records = new ArrayList<>();
        records.add(new Record());
        records.add(new Record());

        when(recordDao.findRecordListByDepartment(department, "2024-2025")).thenReturn(records);
        List<Record> result = recordFacade.getViolationsByDepartment(department, "2024-2025");

        assertEquals(2, result.size());
        verify(recordDao).findRecordListByDepartment(department, "2024-2025");
    }
    @Test
    void testGetRecordByStudentID(){
        List<Record> records = new ArrayList<>();

        when(recordDao.findRecordByStudentId("JHS-0001")).thenReturn(records);
        List<Record> result = recordFacade.getRecordByStudentId("JHS-0001");

        assertEquals(records, result);
    }
    @Test
    void testGetRecentViolations(){
        List<Record> records = new ArrayList<>();
        for (int x = 1; x <= 10; x++) {
            Record r = mock(Record.class);
            records.add(r);
        }
        when(recordDao.findAllBySchoolYear("2025-2026")).thenReturn(records);
        List<Record> result = recordFacade.getRecentViolations("2025-2026", 10);

        assertEquals(10, result.size());
        assertEquals(records.subList(0, 10), result);
    }
}