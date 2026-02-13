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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

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
    void testEditExistingViolation() throws SQLException
    {
        when(preparedStatement.executeUpdate()).thenReturn(1);
        OffenseDao dao = new OffenseDaoImpl();

        boolean status = dao.editExistingViolation("R-001","Resolved",
                "OFF-004", "Bullying incident reported");

        assertTrue(status);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement).setString(1, "Resolved");
        verify(preparedStatement).setString(2, "OFF-004");
        verify(preparedStatement).setString(3, "Bullying incident reported");
        verify(preparedStatement).setString(4, "R-001");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testEditExistingDateOfViolation() throws SQLException
    {
        // java.sql.Date.valueOf("2023-02-09")

        when(preparedStatement.executeUpdate()).thenReturn(1);
        OffenseDao dao = new OffenseDaoImpl();

        boolean status = dao.editExistingDateOfViolation("R-001", java.sql.Date.valueOf("2024-09-15"));

        assertTrue(status);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement).setDate(1, java.sql.Date.valueOf("2024-09-15"));
        verify(preparedStatement).setString(2, "R-001");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testEditExistingDateOfResolution() throws SQLException
    {
        // java.sql.Date.valueOf("2023-02-09")

        when(preparedStatement.executeUpdate()).thenReturn(1);
        OffenseDao dao = new OffenseDaoImpl();

        boolean status = dao.editExistingDateOfResolution("R-001", java.sql.Date.valueOf("2025-01-30"));

        assertTrue(status);
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement).setDate(1, java.sql.Date.valueOf("2025-01-30"));
        verify(preparedStatement).setString(2, "R-001");
        verify(preparedStatement).executeUpdate();
    }
}