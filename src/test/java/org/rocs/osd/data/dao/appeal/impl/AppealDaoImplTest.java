package org.rocs.osd.data.dao.appeal.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.enrollment.Enrollment;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppealDaoImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConnectionHelper> connectionHelper;

    private AppealDaoImpl appealDao;

    @BeforeEach
    void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);

        appealDao = new AppealDaoImpl();
    }

    @AfterEach
    void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testFindAppealsByStatus() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong("appealID")).thenReturn(1L);
        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getLong("enrollmentID")).thenReturn(1L);
        when(resultSet.getString("message")).thenReturn("Test appeal");
        when(resultSet.getDate("dateFiled")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("PENDING");
        when(resultSet.getDate("dateProcessed")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getString("remarks")).thenReturn("Test Remarks");
        when(resultSet.getString("studentID")).thenReturn("S001");
        when(resultSet.getString("firstName")).thenReturn("John");
        when(resultSet.getString("lastName")).thenReturn("Doe");
        when(resultSet.getString("offense")).thenReturn("Late Submission");

        List<Appeal> appeals = appealDao.findAppealsByStatus("PENDING");

        assertNotNull(appeals);
        assertEquals(1, appeals.size());

        Appeal appeal = appeals.get(0);
        assertEquals(1L, appeal.getAppealID());
        assertEquals("Test appeal", appeal.getMessage());
        assertEquals("PENDING", appeal.getStatus());

        Record record = appeal.getRecord();
        assertNotNull(record);
        assertEquals(1L, record.getRecordId());
        assertEquals("Late Submission", record.getRemarks());

        Enrollment enrollment = appeal.getEnrollment();
        assertNotNull(enrollment);
        assertEquals(1L, enrollment.getEnrollmentId());

        Student student = enrollment.getStudent();
        assertNotNull(student);
        assertEquals("S001", student.getStudentId());
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());

        verify(preparedStatement).setString(1, "PENDING");
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testUpdateAppealStatus() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> appealDao.updateAppealStatus(1L, "APPROVED"));

        verify(preparedStatement).setString(1, "APPROVED");
        verify(preparedStatement).setLong(2, 1L);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testSaveRemarks() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> appealDao.saveRemarks(1L, "Invalid remarks"));

        verify(preparedStatement).setString(1, "Invalid remarks");
        verify(preparedStatement).setLong(2, 1L);
        verify(preparedStatement).executeUpdate();
    }
}
