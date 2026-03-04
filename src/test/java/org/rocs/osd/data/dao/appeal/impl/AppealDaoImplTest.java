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

    private static MockedStatic<ConnectionHelper> connectionHelper;

    @BeforeEach
    void setUp() throws SQLException {

        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);

        lenient().when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        lenient().when(connection.prepareStatement(anyString(), any(String[].class))).thenReturn(preparedStatement);
        lenient().when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
    }

    @AfterEach
    void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testSaveAppeal() throws SQLException {

        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(10L);

        AppealDaoImpl dao = new AppealDaoImpl();
        Appeal appeal = new Appeal(1L, 1L, "Test appeal message", "PENDING");

        Appeal saved = dao.saveAppeal(appeal);

        assertNotNull(saved.getAppealID());
        assertEquals(10L, saved.getAppealID());

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testFindById() throws SQLException {

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("appealID")).thenReturn(1L);
        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getLong("enrollmentID")).thenReturn(1L);
        when(resultSet.getString("message")).thenReturn("Sample");
        when(resultSet.getDate("dateFiled")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("PENDING");

        AppealDaoImpl dao = new AppealDaoImpl();
        Appeal appeal = dao.findByAppealId(1L);

        assertNotNull(appeal);
        assertEquals("Sample", appeal.getAppealMessage());

        verify(preparedStatement, times(1)).setLong(1, 1L);
        verify(preparedStatement, times(1)).executeQuery();
    }
    @Test
    void testFindByEnrollmentId() throws SQLException {
        Long enrollmentId = 1L;

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong("appealID")).thenReturn(1L);
        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getLong("enrollmentID")).thenReturn(enrollmentId);
        when(resultSet.getString("message")).thenReturn("Test appeal");
        when(resultSet.getDate("dateFiled")).thenReturn(new Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("PENDING");

        AppealDaoImpl dao = new AppealDaoImpl();
        List<Appeal> appeals = dao.findByEnrollmentId(enrollmentId);

        assertNotNull(appeals);
        assertEquals(1, appeals.size());

        Appeal firstAppeal = appeals.get(0);
        assertEquals(enrollmentId, firstAppeal.getEnrollmentID());
        assertEquals("Test appeal", firstAppeal.getAppealMessage());

        verify(preparedStatement, times(1)).setLong(1, enrollmentId);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testUpdateStatus() throws SQLException {

        when(preparedStatement.executeUpdate()).thenReturn(1);

        AppealDaoImpl dao = new AppealDaoImpl();
        dao.updateAppealStatus(1L, "APPROVED");

        verify(preparedStatement, times(1)).setString(1, "APPROVED");
        verify(preparedStatement, times(1)).setLong(2, 1L);
        verify(preparedStatement, times(1)).executeUpdate();
    }
}