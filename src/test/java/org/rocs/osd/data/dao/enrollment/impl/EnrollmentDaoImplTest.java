package org.rocs.osd.data.dao.enrollment.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.enrollment.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class EnrollmentDaoImplTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private EnrollmentDao enrollmentDao;

    private static MockedStatic<ConnectionHelper> connectionHelper;

    @BeforeEach
    public void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(this.connection);
        Mockito.when(this.connection.prepareStatement(ArgumentMatchers.anyString())).thenReturn(this.preparedStatement);
        enrollmentDao = new EnrollmentDaoImpl();

    }
    @AfterEach
    public void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testFindEnrollmentIdByStudentId() throws SQLException{
        Mockito.when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        Mockito.when(this.resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(this.resultSet.getLong("departmentId")).thenReturn(3L);
        Mockito.when(this.resultSet.getString("departmentName")).thenReturn("College Department");
        Enrollment enrollment = enrollmentDao.findEnrollmentIdByStudentId(1);

    }
}