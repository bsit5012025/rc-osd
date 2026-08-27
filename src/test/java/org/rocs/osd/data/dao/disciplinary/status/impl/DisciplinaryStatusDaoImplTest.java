package org.rocs.osd.data.dao.disciplinary.status.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.disciplinary.status.DisciplinaryStatusDao;
import org.rocs.osd.model.disciplinary.status.DisciplinaryStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisciplinaryStatusDaoImplTest {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private static MockedStatic<ConnectionHelper> connectionHelper;

    private DisciplinaryStatusDao disciplinaryStatusDao;

    @BeforeEach
    public void setUp() throws SQLException {
        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        disciplinaryStatusDao = new DisciplinaryStatusDaoImpl();
    }

    @AfterEach
    public void tearDown() {
        connectionHelper.close();
    }

    @Test
    void TestGetAllDisciplinaryStatus () throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);

        when(resultSet.getLong("DisciplinaryStatusID"))
                .thenReturn(1L);
        when(resultSet.getString("Status"))
                .thenReturn("Good Standing");
        when(resultSet.getString("Description"))
                .thenReturn("Student has no disciplinary issues and maintains good behavior.");

        List<DisciplinaryStatus> array = disciplinaryStatusDao.getAllDisciplinaryStatus();
        DisciplinaryStatus disciplinaryStatus = array.get(0);

        assertEquals(1L, disciplinaryStatus.getDisciplinaryStatusId());
        assertEquals("Good Standing", disciplinaryStatus.getStatus());
        assertEquals(
                "Student has no disciplinary issues and maintains good behavior.",
                disciplinaryStatus.getDescription());
        verify(preparedStatement).executeQuery();
    }
}