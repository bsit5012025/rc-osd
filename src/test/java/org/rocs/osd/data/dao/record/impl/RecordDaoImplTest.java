package org.rocs.osd.data.dao.record.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordDaoImplTest
{
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;
    private RecordDaoImpl recordDao;

    private static MockedStatic<ConnectionHelper> connectionHelper;

    @BeforeEach
    public void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        recordDao = new RecordDaoImpl();
    }

    @AfterEach
    public void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testFindAllBySchoolYear() throws Exception {
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);

            when(resultSet.getLong("recordID")).thenReturn(1L);
            when(resultSet.getDate("dateOfViolation")).thenReturn(Date.valueOf("2024-09-15"));
            when(resultSet.getDate("dateOfResolution")).thenReturn(Date.valueOf("2024-09-20"));
            when(resultSet.getString("remarks")).thenReturn("Student caught vaping in school");
            when(resultSet.getString("status")).thenReturn("PENDING");

            when(resultSet.getLong("enrollmentID")).thenReturn(1L);
            when(resultSet.getString("schoolYear")).thenReturn("2025-2026");
            when(resultSet.getString("studentLevel")).thenReturn("Grade-8");
            when(resultSet.getString("section")).thenReturn("St. Hannibal");
            when(resultSet.getString("studId")).thenReturn("STUD-001");
            when(resultSet.getString("firstName")).thenReturn("John");
            when(resultSet.getString("lastName")).thenReturn("Doe");
            when(resultSet.getLong("studentPersonID")).thenReturn(2L);
            when(resultSet.getString("studentAddress")).thenReturn("Buho");
            when(resultSet.getString("studentType")).thenReturn("Intern");

            when(resultSet.getString("disciplinaryStatus")).thenReturn("Good Standing");
            when(resultSet.getString("statusDescription")).thenReturn("No issues");

            when(resultSet.getString("employeeID")).thenReturn("EMP-002");
            when(resultSet.getLong("empPersonID")).thenReturn(9L);
            when(resultSet.getString("empDept")).thenReturn("JHS");
            when(resultSet.getString("employeeRole")).thenReturn("PREFECT");

            when(resultSet.getLong("offenseID")).thenReturn(1L);
            when(resultSet.getString("offense")).thenReturn("Vaping");
            when(resultSet.getString("offenseType")).thenReturn("Major Offense");
            when(resultSet.getString("offenseDescription")).thenReturn("Bringing vape");

            when(resultSet.getLong("actionID")).thenReturn(1L);
            when(resultSet.getString("action")).thenReturn("Community Service");
            when(resultSet.getString("actionDescription")).thenReturn("Service work");

            RecordDaoImpl dao = new RecordDaoImpl();
            List<Record> records = dao.findAllBySchoolYear("2025-2026");

            assertFalse(records.isEmpty());

            Record record = records.getFirst();

            assertEquals(1L, record.getRecordId());
            assertEquals("Student caught vaping in school", record.getRemarks());
            assertEquals(RecordStatus.PENDING, record.getStatus());

            assertEquals(1L, record.getEnrollment().getEnrollmentId());
            assertEquals("Grade-8", record.getEnrollment().getStudentLevel());
            assertEquals("EMP-002", record.getEmployee().getEmployeeId());
            assertEquals("Vaping", record.getOffense().getOffense());
            assertEquals("Community Service", record.getAction().getActionName());

            verify(preparedStatement).setString(1, "2025-2026");
            verify(preparedStatement).executeQuery();
        }

    @Test
    void testAddStudentRecord() throws SQLException
    {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        RecordDao dao = new RecordDaoImpl();
        boolean status = dao.addStudentRecord(Long.valueOf(3), "EMP-002",
                Long.valueOf(4), Date.valueOf("2025-03-08"),Long.valueOf(2),
                "Bullying incident reported", RecordStatus.PENDING);

        assertTrue(status);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement).setLong(1, Long.valueOf(3));
        verify(preparedStatement).setString(2, "EMP-002");
        verify(preparedStatement).setLong(3, Long.valueOf(4));
        verify(preparedStatement).setDate(4, Date.valueOf("2025-03-08"));
        verify(preparedStatement).setLong(5, Long.valueOf(2));
        verify(preparedStatement).setString(6, "Bullying incident reported");
        verify(preparedStatement).setString(7, String.valueOf(RecordStatus.PENDING));
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testUpdateRecord() throws SQLException
    {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        RecordDao dao = new RecordDaoImpl();

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(Long.valueOf(1));

        Employee employee = new Employee();
        employee.setEmployeeId("EMP-002");

        Offense offense = new Offense();
        offense.setOffenseId(Long.valueOf(1));

        DisciplinaryAction action = new DisciplinaryAction();
        action.setActionId(Long.valueOf(1));

        Record record = new Record();
        record.setEnrollment(enrollment);
        record.setEmployee(employee);
        record.setOffense(offense);
        record.setDateOfViolation(java.sql.Date.valueOf("2024-09-15"));
        record.setDateOfResolution(java.sql.Date.valueOf("2025-06-15"));
        record.setAction(action);
        record.setRemarks("Student caught vaping in school");
        record.setStatus(RecordStatus.PENDING);
        record.setRecordId(Long.valueOf(1));

        boolean status = dao.updateRecord(record);

        assertTrue(status);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement).setLong(1, Long.valueOf(1));
        verify(preparedStatement).setString(2,"EMP-002");
        verify(preparedStatement).setLong(3, Long.valueOf(1));
        verify(preparedStatement).setDate(4, java.sql.Date.valueOf("2024-09-15"));
        verify(preparedStatement).setDate(5, java.sql.Date.valueOf("2025-06-15"));
        verify(preparedStatement).setLong(6, Long.valueOf(1));
        verify(preparedStatement).setString(7, "Student caught vaping in school");
        verify(preparedStatement).setString(8, String.valueOf(RecordStatus.PENDING));
        verify(preparedStatement).setLong(9, Long.valueOf(1));
        verify(preparedStatement).executeUpdate();
    }
    @Test
    void testFindRecordListByDepartmentReturnListOfRecords() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getDate("dateOfViolation")).thenReturn(Date.valueOf("2025-01-12"));
        when(resultSet.getDate("dateOfResolution")).thenReturn(Date.valueOf("2025-01-20"));
        when(resultSet.getString("remarks")).thenReturn("Repeatedly late to class");
        when(resultSet.getString("status")).thenReturn("RESOLVED");

        when(resultSet.getLong("enrollmentID")).thenReturn(2L);
        when(resultSet.getString("studentID")).thenReturn("JHS-0001");
        when(resultSet.getString("schoolYear")).thenReturn("2025-2026");
        when(resultSet.getString("studentLevel")).thenReturn("Grade-9");
        when(resultSet.getString("section")).thenReturn("St. Anthony");
        when(resultSet.getString("department")).thenReturn("JHS");

        when(resultSet.getString("firstName")).thenReturn("Carl");
        when(resultSet.getString("middleName")).thenReturn("D");
        when(resultSet.getString("lastName")).thenReturn("Cain");

        when(resultSet.getString("offense")).thenReturn("Tardiness");
        when(resultSet.getString("type")).thenReturn("Major Offense");

        when(resultSet.getString("employeeID")).thenReturn("EMP-002");
        when(resultSet.getString("empFirstName")).thenReturn("Jun");
        when(resultSet.getString("empLastName")).thenReturn("Cadorna");

        when(resultSet.getString("action")).thenReturn("Probation");

        RecordDaoImpl dao = new RecordDaoImpl();

        List<Record> records = dao.findRecordListByDepartment(Department.JHS,"2025-2026");

        assertEquals(1, records.size());

        Record record = records.getFirst();

        assertEquals(1L, record.getRecordId());
        assertEquals("Repeatedly late to class", record.getRemarks());
        assertEquals(RecordStatus.RESOLVED, record.getStatus());
        assertEquals("Tardiness", record.getOffense().getOffense());
        assertEquals("EMP-002", record.getEmployee().getEmployeeId());
        assertEquals("Probation", record.getAction().getActionName());

        verify(preparedStatement).setString(1, Department.JHS.name());
        verify(preparedStatement).executeQuery();
    }
    @Test
    void testFindTotalViolationsReturnTotalCountOfViolation() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(1)).thenReturn(10);

        int result = recordDao.findTotalViolations("2024-2025");

        assertEquals(10, result);

        verify(preparedStatement).executeQuery();
        verify(resultSet).getInt(1);
    }
    @Test
    void testFindTodaysCountViolationReturnTotalCountOfTodayViolation() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(1)).thenReturn(3);

        int result = recordDao.findTodayViolations();
        

        assertEquals(3, result);

        verify(preparedStatement).executeQuery();
    }
    @Test
    void testFindFrequentOffenseComittedReturnListOfFrequentOffenseComitted() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getString("offense"))
                .thenReturn("Cheating", "Punching");

        when(resultSet.getInt("total"))
                .thenReturn(5, 3);

        Map<String, Integer> result = recordDao.findMostFrequentOffenses("2024-2025");

        assertEquals(2, result.size());
        assertEquals(5, result.get("Cheating"));
        assertEquals(3, result.get("Punching"));

        verify(preparedStatement).executeQuery();
    }
    @Test
    void testFindRecordsByStudentIdReturnRecordsOfStudent() throws  SQLException{
        when(connection.prepareStatement((anyString()))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getDate("dateOfViolation")).thenReturn(Date.valueOf("2025-01-12"));
        when(resultSet.getDate("dateOfResolution")).thenReturn(Date.valueOf("2025-01-20"));
        when(resultSet.getString("remarks")).thenReturn("Repeatedly late to class");
        when(resultSet.getString("status")).thenReturn("RESOLVED");

        when(resultSet.getLong("enrollmentID")).thenReturn(2L);
        when(resultSet.getString("studentID")).thenReturn("JHS-0001");
        when(resultSet.getString("schoolYear")).thenReturn("2025-2026");
        when(resultSet.getString("studentLevel")).thenReturn("Grade-9");
        when(resultSet.getString("section")).thenReturn("St. Anthony");

        when(resultSet.getString("firstName")).thenReturn("Carl");
        when(resultSet.getString("middleName")).thenReturn("D");
        when(resultSet.getString("lastName")).thenReturn("Cain");

        when(resultSet.getString("offense")).thenReturn("Tardiness");
        when(resultSet.getString("type")).thenReturn("Major Offense");

        when(resultSet.getString("action")).thenReturn("Probation");

        List<Record> records = recordDao.findRecordByStudentId("JHS-0001");

        assertNotNull(records);
        assertEquals(1, records.size());

        Record record = records.get(0);

        assertEquals("JHS-0001", record.getEnrollment().getStudent().getStudentId());
        assertEquals("Carl", record.getEnrollment().getStudent().getFirstName());
        assertEquals("Tardiness", record.getOffense().getOffense());
        assertEquals("Probation", record.getAction().getActionName());

    }

}