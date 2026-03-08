package org.rocs.osd.data.dao.appeal.impl;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;

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

    private MockedStatic<ConnectionHelper> connectionHelper;

    private AppealDaoImpl appealDao;

    @BeforeEach
    void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);

        appealDao = new AppealDaoImpl();
    }

    @AfterEach
    void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testFindPendingAppealsWithDetails() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getLong("appealID")).thenReturn(1L);
        when(resultSet.getLong("recordID")).thenReturn(1L);
        when(resultSet.getLong("enrollmentID")).thenReturn(1L);
        when(resultSet.getString("message")).thenReturn("Test appeal");
        when(resultSet.getDate("dateFiled")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getString("status")).thenReturn("PENDING");
        when(resultSet.getString("studentID")).thenReturn("S001");
        when(resultSet.getString("firstName")).thenReturn("John");
        when(resultSet.getString("lastName")).thenReturn("Doe");
        when(resultSet.getString("offense")).thenReturn("Late Submission");

        List<Object[]> appeals = appealDao.findPendingAppealsWithDetails();

        assertNotNull(appeals);
        assertEquals(1, appeals.size());

        Object[] row = appeals.get(0);
        assertInstanceOf(Appeal.class, row[0]);
        assertInstanceOf(Student.class, row[1]);
        assertInstanceOf(Offense.class, row[2]);

        Appeal appeal = (Appeal) row[0];
        assertEquals(1L, appeal.getAppealID());
        assertEquals("Test appeal", appeal.getMessage());
        assertEquals("PENDING", appeal.getStatus());

        Student student = (Student) row[1];
        assertEquals("S001", student.getStudentId());
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());

        Offense offense = (Offense) row[2];
        assertEquals("Late Submission", offense.getOffense());

        verify(preparedStatement).executeQuery();
    }

    @Test
    void testUpdateAppealStatus() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> appealDao.updateAppealStatus(1L, "APPROVED"));

        verify(preparedStatement).setString(1, "APPROVED");
        verify(preparedStatement).setLong(2, 1L);
        verify(preparedStatement).executeUpdate();
    }
}
