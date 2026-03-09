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
import org.rocs.osd.model.enrollment.Enrollment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    void testFindListOfEnrollmentsByStudentIdReturnListOfEnrollmentOfStudent() throws SQLException{
        when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        when(this.resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(this.resultSet.getLong("enrollmentID")).thenReturn(1L, 2L);
        when(this.resultSet.getString("schoolYear")).thenReturn("2024-2025", "2025-2026");
        when(this.resultSet.getString("studentLevel")).thenReturn("Grade 8", "Grade 9");
        when(this.resultSet.getString("section")).thenReturn("St. Hannibal", "St. Anthony");
        when(this.resultSet.getString("studentID")).thenReturn("JHS-0001", "JHS-0001");
        when(this.resultSet.getLong("departmentID")).thenReturn(1L, 1L);
        when(this.resultSet.getLong("disciplinaryStatusID")).thenReturn(1L, 2L);
        when(this.resultSet.getLong("personID")).thenReturn(1L,1L);
        when(this.resultSet.getString("address")).thenReturn("Buho","Buho");
        when(this.resultSet.getString("studentDepartmentID")).thenReturn("1","1");

        List<Enrollment> result = enrollmentDao.findEnrollmentsByStudentId("JHS-0001");

        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    void testFindEnrollmentByStudentIdReturnEnrollment() throws SQLException{
        when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        when(this.resultSet.next()).thenReturn(true);
        when(this.resultSet.getLong("enrollmentID")).thenReturn(1L);

        long result = enrollmentDao.findEnrollmentIdByStudentId("JHS-0001");

        assertEquals(1L, result);
    }
}