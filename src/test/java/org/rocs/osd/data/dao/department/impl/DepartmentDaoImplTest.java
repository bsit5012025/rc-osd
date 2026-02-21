package org.rocs.osd.data.dao.department.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.department.DepartmentDao;
import org.rocs.osd.model.department.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@ExtendWith({MockitoExtension.class})
class DepartmentDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private DepartmentDao departmentDao;

    private static MockedStatic<ConnectionHelper> connectionHelper;

    @BeforeEach
    public void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(this.connection);
        Mockito.when(this.connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(this.preparedStatement);
        departmentDao = new DepartmentDaoImpl();
    }
    @AfterEach
    public void tearDown() {
        connectionHelper.close();
    }

    @Test
    void TestFindDepartmentNameByIdIfFound() throws SQLException{
        Mockito.when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        Mockito.when(this.resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(this.resultSet.getLong("departmentId")).thenReturn(3L);
        Mockito.when(this.resultSet.getString("departmentName")).thenReturn("College Department");
        Department department = departmentDao.findDepartmentById(3L);

        Assertions.assertEquals(3L, department.getDepartmentId());
        Assertions.assertEquals("College Department", department.getDepartmentName());

        Mockito.verify(this.connection, Mockito.times(1)).prepareStatement(ArgumentMatchers.anyString());
        Mockito.verify(this.preparedStatement, Mockito.times(1)).setLong(1, 3L);
        Mockito.verify(this.preparedStatement, Mockito.times(1)).executeQuery();
    }

    @Test
    void TestfindAllDepartmentName() throws SQLException{
        Mockito.when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        Mockito.when(this.resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.resultSet.getString("departmentName")).thenReturn("Junior High School Department")
                .thenReturn("Senior High School Department");
        List<String> departmentNames = departmentDao.findAllDepartmentName();
        List<String> expectDepartmentNames = new ArrayList<>();
        expectDepartmentNames.add("Junior High School Department");
        expectDepartmentNames.add("Senior High School Department");

        Assertions.assertEquals(expectDepartmentNames,departmentNames);
        Mockito.verify(this.connection).prepareStatement(Mockito.anyString());
        Mockito.verify(this.preparedStatement).executeQuery();

    }
}