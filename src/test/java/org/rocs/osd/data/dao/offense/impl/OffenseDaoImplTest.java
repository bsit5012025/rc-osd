package org.rocs.osd.data.dao.offense.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;

import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OffenseDaoImplTest
{
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private static MockedStatic<ConnectionHelper> connectionHelper;

    @BeforeEach
    public void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @AfterEach
    public void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testGetAllStudent() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("studentID")).thenReturn("JHS-0001");
        when(resultSet.getLong("personID")).thenReturn(Long.valueOf(2));
        when(resultSet.getString("address")).thenReturn("Buho");
        when(resultSet.getString("studentType")).thenReturn("Intern");
        when(resultSet.getString("departmentID")).thenReturn("jhs-3001");
        when(resultSet.getString("lastName")).thenReturn("userLastName");
        when(resultSet.getString("middleName")).thenReturn("userMidName");
        when(resultSet.getString("firstName")).thenReturn("userFirstName");

        OffenseDao dao = new OffenseDaoImpl();
        ArrayList<Student> studentList  = dao.getAllStudent();
        Student student = studentList.get(0);

        assertEquals("JHS-0001", student.getStudentId());
        assertEquals(Long.valueOf(2), student.getPersonID());
        assertEquals("Buho", student.getAddress());
        assertEquals("Intern", student.getStudentType());
        assertEquals("jhs-3001", student.getDepartmentId());
        assertEquals("userLastName", student.getLastName());
        assertEquals("userMidName", student.getMiddleName());
        assertEquals("userFirstName", student.getFirstName());

        verify(connection, times(1)).prepareStatement(anyString());
        // verify(preparedStatement, times(1)).setString(1, "JHS-0001");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetStudentByDepartmentID() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("studentID")).thenReturn("JHS-0001");
        when(resultSet.getLong("personID")).thenReturn(Long.valueOf(2));
        when(resultSet.getString("address")).thenReturn("Buho");
        when(resultSet.getString("studentType")).thenReturn("Intern");
        when(resultSet.getString("departmentID")).thenReturn("jhs-3001");
        when(resultSet.getString("lastName")).thenReturn("userLastName");
        when(resultSet.getString("middleName")).thenReturn("userMidName");
        when(resultSet.getString("firstName")).thenReturn("userFirstName");

        OffenseDao dao = new OffenseDaoImpl();
        ArrayList<Student> studentlist  = dao.getStudentByDepartmentID(Long.valueOf(3));
        Student student = studentlist.get(0);

        assertEquals("JHS-0001", student.getStudentId());
        assertEquals(Long.valueOf(2), student.getPersonID());
        assertEquals("Buho", student.getAddress());
        assertEquals("Intern", student.getStudentType());
        assertEquals("jhs-3001", student.getDepartmentId());
        assertEquals("userLastName", student.getLastName());
        assertEquals("userMidName", student.getMiddleName());
        assertEquals("userFirstName", student.getFirstName());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setLong(1, Long.valueOf(3));
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetStudentById() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("studentID")).thenReturn("JHS-0001");
        when(resultSet.getLong("personID")).thenReturn(2L);
        when(resultSet.getString("address")).thenReturn("Buho");
        when(resultSet.getString("studentType")).thenReturn("Intern");
        when(resultSet.getString("departmentID")).thenReturn("jhs-3001");
        when(resultSet.getString("lastName")).thenReturn("userLastName");
        when(resultSet.getString("middleName")).thenReturn("userMidName");
        when(resultSet.getString("firstName")).thenReturn("userFirstName");

        OffenseDao dao = new OffenseDaoImpl();
        Student student  = dao.getStudentById("JHS-0001");

        assertEquals("JHS-0001", student.getStudentId());
        assertEquals(2L, student.getPersonID());
        assertEquals("Buho", student.getAddress());
        assertEquals("Intern", student.getStudentType());
        assertEquals("jhs-3001", student.getDepartmentId());
        assertEquals("userLastName", student.getLastName());
        assertEquals("userMidName", student.getMiddleName());
        assertEquals("userFirstName", student.getFirstName());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "JHS-0001");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetStudentByName() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("studentID")).thenReturn("JHS-0001");
        when(resultSet.getLong("personID")).thenReturn(2L);
        when(resultSet.getString("address")).thenReturn("Buho");
        when(resultSet.getString("studentType")).thenReturn("Intern");
        when(resultSet.getString("departmentID")).thenReturn("jhs-3001");
        when(resultSet.getString("lastName")).thenReturn("userLastName");
        when(resultSet.getString("middleName")).thenReturn("userMidName");
        when(resultSet.getString("firstName")).thenReturn("userFirstName");

        OffenseDao dao = new OffenseDaoImpl();
        Student student  = dao.getStudentByName("userLastName", "userFirstName", "userMidName");

        assertEquals("JHS-0001", student.getStudentId());
        assertEquals(2L, student.getPersonID());
        assertEquals("Buho", student.getAddress());
        assertEquals("Intern", student.getStudentType());
        assertEquals("jhs-3001", student.getDepartmentId());
        assertEquals("userLastName", student.getLastName());
        assertEquals("userMidName", student.getMiddleName());
        assertEquals("userFirstName", student.getFirstName());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "userLastName");
        verify(preparedStatement, times(1)).setString(2, "userFirstName");
        verify(preparedStatement, times(1)).setString(3, "userMidName");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetStudentRecord() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("recordID")).thenReturn(Long.valueOf(1));
        when(resultSet.getLong("enrollmentID")).thenReturn(Long.valueOf(1));
        when(resultSet.getString("employeeID")).thenReturn("EMP-002");
        when(resultSet.getLong("offenseID")).thenReturn(Long.valueOf(1));
        when(resultSet.getDate("dateOfViolation")).thenReturn(Date.valueOf("2024-09-15"));
        when(resultSet.getLong("actionID")).thenReturn(Long.valueOf(1));
        when(resultSet.getDate("dateOfResolution")).thenReturn(Date.valueOf("2024-09-20"));
        when(resultSet.getString("remarks")).thenReturn("Student caught vaping in school");
        when(resultSet.getString("status")).thenReturn("Pending");

        OffenseDao dao = new OffenseDaoImpl();
        ArrayList<Record> studentRecordList = dao.getStudentRecord("CT123");
        Record record = studentRecordList.get(0);

        assertFalse(studentRecordList.isEmpty());
        assertNotNull(record);
        assertEquals(1, record.getRecordId());
        assertEquals(1, record.getEnrollmentId());
        assertEquals("EMP-002", record.getEmployeeId());
        assertEquals(1, record.getOffenseId());
        assertEquals(Date.valueOf("2024-09-15"), record.getDateOfViolation());
        assertEquals(1, record.getActionId());
        assertEquals(Date.valueOf("2024-09-20"), record.getDateOfResolution());
        assertEquals("Student caught vaping in school", record.getRemarks());
        assertEquals("Pending", record.getStatus());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "CT123");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testGetStudentOffense() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("offenseID")).thenReturn(Long.valueOf(1));
        when(resultSet.getString("offense")).thenReturn("Vaping");
        when(resultSet.getString("type")).thenReturn("Major Offense");
        when(resultSet.getString("description")).thenReturn("Bringing vape");

        OffenseDao dao = new OffenseDaoImpl();
        Offense offense = dao.getStudentOffense("OFF-001");

        assertEquals(1, offense.getOffenseId());
        assertEquals("Vaping", offense.getOffense());
        assertEquals("Major Offense", offense.getType());
        assertEquals("Bringing vape", offense.getDescription());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "OFF-001");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testAddStudentViolation() throws SQLException
    {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        OffenseDao dao = new OffenseDaoImpl();
        boolean status = dao.addStudentViolation(Long.valueOf(3), "EMP-002",
                Long.valueOf(4), Date.valueOf("2025-03-08"),Long.valueOf(2),
                "Bullying incident reported","Resolved");

        assertTrue(status);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement).setLong(1, Long.valueOf(3));
        verify(preparedStatement).setString(2, "EMP-002");
        verify(preparedStatement).setLong(3, Long.valueOf(4));
        verify(preparedStatement).setDate(4, Date.valueOf("2025-03-08"));
        verify(preparedStatement).setLong(5, Long.valueOf(2));
        verify(preparedStatement).setString(6, "Bullying incident reported");
        verify(preparedStatement).setString(7, "Resolved");
        verify(preparedStatement).executeUpdate();
    }
}