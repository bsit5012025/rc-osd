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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppealDaoImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConnectionHelper> connectionHelper;

    @BeforeEach
    void setUp() throws SQLException {

        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @AfterEach
    void tearDown() {
        connectionHelper.close();
    }

    @Test
    @Order(1)
    void testSaveAppeal() throws SQLException {

        when(preparedStatement.executeUpdate()).thenReturn(1);

        AppealDaoImpl dao = new AppealDaoImpl();

        Appeal appeal = new Appeal();
        appeal.setRecordID(1L);
        appeal.setEnrollmentID(1L);
        appeal.setMessage("Test appeal message");
        appeal.setDateFiled(new Date());
        appeal.setStatus("PENDING");

        assertDoesNotThrow(() -> dao.saveAppeal(appeal));
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    @Order(2)
    void testFindAllAppealDetails() throws SQLException {

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getLong("appealID")).thenReturn(1L);
        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getLong("enrollmentID")).thenReturn(1L);
        when(resultSet.getString("message")).thenReturn("Test appeal message");
        when(resultSet.getDate("dateFiled")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("PENDING");

        AppealDaoImpl dao = new AppealDaoImpl();
        List<Appeal> appeals = dao.findAllAppealDetails();

        assertNotNull(appeals);
        assertEquals(1, appeals.size());

        Appeal appeal = appeals.get(0);
        assertEquals(1L, appeal.getAppealID());
        assertEquals("Test appeal message", appeal.getMessage());
        assertEquals("PENDING", appeal.getStatus());

        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    @Order(3)
    void testUpdateAppealStatus() throws SQLException {

        when(preparedStatement.executeUpdate()).thenReturn(1);
        AppealDaoImpl dao = new AppealDaoImpl();

        assertDoesNotThrow(() -> dao.updateAppealStatus(1L, "APPROVED"));

        verify(preparedStatement, times(1)).setString(1, "APPROVED");
        verify(preparedStatement, times(1)).setLong(2, 1L);
        verify(preparedStatement, times(1)).executeUpdate();
    }
}