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
import org.rocs.osd.model.person.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentDaoImplTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MockedStatic<ConnectionHelper> connectionHelper;

    private StudentDaoImpl dao;

    @BeforeEach
    void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        dao = new StudentDaoImpl();
    }

    @AfterEach
    void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testFindAllStudents_returnsList() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getLong("personID")).thenReturn(1L);
        when(resultSet.getString("studentID")).thenReturn("SHS-001");
        when(resultSet.getString("address")).thenReturn("Address 1");
        when(resultSet.getString("studentType")).thenReturn("INTERN");
        when(resultSet.getLong("departmentID")).thenReturn(2L);
        when(resultSet.getString("firstName")).thenReturn("John");
        when(resultSet.getString("middleName")).thenReturn("M");
        when(resultSet.getString("lastName")).thenReturn("Doe");

        List<Student> students = dao.findAllStudents();

        assertNotNull(students);
        assertEquals(2, students.size());

        Student first = students.getFirst();
        assertEquals(1L, first.getPersonID());
        assertEquals("SHS-001", first.getStudentId());
        assertEquals("John", first.getFirstName());
        assertEquals("M", first.getMiddleName());
        assertEquals("Doe", first.getLastName());
        assertEquals("Address 1", first.getAddress());
        assertEquals("INTERN", first.getStudentType());
        assertEquals(2L, first.getDepartmentId());

        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
    }


    @Test
    void testFindStudentByID_found() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getLong("personID")).thenReturn(2L);
        when(resultSet.getString("studentID")).thenReturn("JHS-002");
        when(resultSet.getString("address")).thenReturn("Address 2");
        when(resultSet.getString("studentType")).thenReturn("EXTERN");
        when(resultSet.getLong("departmentID")).thenReturn(1L);
        when(resultSet.getString("firstName")).thenReturn("Jane");
        when(resultSet.getString("middleName")).thenReturn("A");
        when(resultSet.getString("lastName")).thenReturn("Smith");

        Student student = dao.findStudentByID("JHS-002");

        assertNotNull(student);
        assertEquals(2L, student.getPersonID());
        assertEquals("JHS-002", student.getStudentId());
        assertEquals("Jane", student.getFirstName());
        assertEquals("A", student.getMiddleName());
        assertEquals("Smith", student.getLastName());
        assertEquals("Address 2", student.getAddress());
        assertEquals("EXTERN", student.getStudentType());
        assertEquals(1L, student.getDepartmentId());

        verify(preparedStatement).setString(1, "JHS-002");
        verify(preparedStatement).executeQuery();
    }

    @Test
    void testFindStudentByID_notFound() throws SQLException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Student student = dao.findStudentByID("UNKNOWN");

        assertNull(student);
    }
}