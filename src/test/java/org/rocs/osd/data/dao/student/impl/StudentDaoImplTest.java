package org.rocs.osd.data.dao.student.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.student.StudendDao;
import org.rocs.osd.model.person.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentDaoImplTest
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
    void testFindStudentById() throws SQLException
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

        StudendDao dao = new StudentDaoImpl();
        Student student  = dao.findStudentWithRecordById("JHS-0001");

        assertEquals("JHS-0001", student.getStudentId());
        assertEquals(Long.valueOf(2), student.getPersonID());
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
    void testFindStudentByName() throws SQLException
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

        StudendDao dao = new StudentDaoImpl();
        Student student  = dao.findStudentWithRecordByName("userLastName", "userFirstName", "userMidName");

        assertEquals("JHS-0001", student.getStudentId());
        assertEquals(Long.valueOf(2), student.getPersonID());
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


}