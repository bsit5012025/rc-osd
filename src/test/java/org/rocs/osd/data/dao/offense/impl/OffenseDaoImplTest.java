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
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.data.dao.login.impl.LoginDaoImpl;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.login.Login;
import org.rocs.osd.model.login.Student;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
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
    void testGetStudentById() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("studentName")).thenReturn("user");;
        when(resultSet.getString("offense_type")).thenReturn("Major Offense");
        when(resultSet.getDate("dateOfViolation")).thenReturn(java.sql.Date.valueOf("2024-09-15"));

        OffenseDao dao = new OffenseDaoImpl();
        Student student = dao.getStudentById("CT123");

        assertNotNull(student);
        assertEquals("user", student.getStudentName());
        assertEquals("Major Offense", student.getOffenseType());
        assertEquals(java.sql.Date.valueOf("2024-09-15"), student.getDateOfViolation());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "CT123");
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    void testAddStudentViolation() throws SQLException
    {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        OffenseDao dao = new OffenseDaoImpl();
        dao.addStudentViolation("R-005", "E-003", "DO-001", "OFF-004",
                "2025-03-08", "D-002", "Bullying incident reported", "Resolved"
        );

        verify(connection, times(1)).prepareStatement(anyString());

        verify(preparedStatement).setString(1, "R-005");
        verify(preparedStatement).setString(2, "E-003");
        verify(preparedStatement).setString(3, "DO-001");
        verify(preparedStatement).setString(4, "OFF-004");
        verify(preparedStatement).setString(5, "2025-03-08");
        verify(preparedStatement).setString(6, "D-002");
        verify(preparedStatement).setString(7, "Bullying incident reported");
        verify(preparedStatement).setString(8, "Resolved");
        verify(preparedStatement).executeUpdate();
    }
}