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
import org.rocs.osd.model.record.Record;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecordDaoImpTest
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
    void testFindStudentByIdAndEnrolment() throws SQLException
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

        RecordDao dao = new RecordDaoImp();
        List<Record> studentRecordList = dao.findStudentByIdAndEnrolment("CT123", "2025-2026", "Grade-8");
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
    void testAddStudentRecord() throws SQLException
    {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        RecordDao dao = new RecordDaoImp();
        boolean status = dao.addStudentRecord(Long.valueOf(3), "EMP-002",
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