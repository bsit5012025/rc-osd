package org.rocs.osd.data.dao.appeal.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.model.appeal.Appeal;

import java.sql.*;
import java.util.Date;
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

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        appealDao = new AppealDaoImpl();
    }

    @AfterEach
    void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testSaveAppeal() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        Appeal appeal = new Appeal();
        appeal.setRecordID(1L);
        appeal.setEnrollmentID(100L);
        appeal.setMessage("Test appeal");
        appeal.setDateFiled(new Date());
        appeal.setStatus("PENDING");

        assertDoesNotThrow(() -> appealDao.saveAppeal(appeal));

        verify(preparedStatement).setLong(1, 1L);
        verify(preparedStatement).setLong(2, 100L);
        verify(preparedStatement).setString(3, "Test appeal");
        verify(preparedStatement).setString(5, "PENDING");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testFindAllAppealDetails() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong("appealID")).thenReturn(1L);
        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getLong("enrollmentID")).thenReturn(100L);
        when(resultSet.getString("message")).thenReturn("Test appeal");
        when(resultSet.getDate("dateFiled")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("PENDING");
        when(resultSet.getString("studentID")).thenReturn("S001");
        when(resultSet.getString("fullName")).thenReturn("John Doe");
        when(resultSet.getString("offense")).thenReturn("Late Submission");

        List<Appeal> appeals = appealDao.findAllAppealDetails();

        assertNotNull(appeals);
        assertEquals(1, appeals.size());

        Appeal appeal = appeals.get(0);
        assertEquals(1L, appeal.getAppealID());
        assertEquals("Test appeal", appeal.getMessage());
        assertEquals("PENDING", appeal.getStatus());
        assertEquals("John Doe", appeal.getStudentName());
        assertEquals("Late Submission", appeal.getOffense());

        verify(preparedStatement).executeQuery();
    }

    @Test
    void testUpdateAppealStatus() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> appealDao.updateAppealStatus(1L, "APPROVED"));

        verify(preparedStatement).setString(1, "APPROVED");
        verify(preparedStatement).setLong(2, 1L);
        verify(preparedStatement).executeUpdate();
    }
}
