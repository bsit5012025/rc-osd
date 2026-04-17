package org.rocs.osd.data.dao.employee.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.person.employee.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeDaoImplTest
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
    void testFindEmployeeByEmployeeID() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getString("employeeID")).thenReturn("EMP-0001");
        when(resultSet.getString("department")).thenReturn(String.valueOf(Department.JHS));
        when(resultSet.getString("employeeRole")).thenReturn("EMP-001");
        when(resultSet.getLong("personID")).thenReturn(1L);
        when(resultSet.getString("firstName")).thenReturn("firstname");
        when(resultSet.getString("middleName")).thenReturn("midname");
        when(resultSet.getString("lastName")).thenReturn("lastname");

        EmployeeDaoImpl dao = new EmployeeDaoImpl();
        Employee employee = dao.findEmployeeByEmployeeID("EMP-0001");

        assertNotNull(employee);
        assertEquals("EMP-0001", employee.getEmployeeId());
        assertEquals(String.valueOf(Department.JHS), employee.getDepartment().name());
        assertEquals("EMP-001", employee.getEmployeeRole());
        assertEquals(1L, employee.getPersonID());
        assertEquals("firstname", employee.getFirstName());
        assertEquals("midname", employee.getMiddleName());
        assertEquals("lastname", employee.getLastName());
    }
}