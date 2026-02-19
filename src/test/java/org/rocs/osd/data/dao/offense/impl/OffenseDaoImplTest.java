package org.rocs.osd.data.dao.offense.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;

import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testFindOffenseById() throws SQLException
    {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("offenseID")).thenReturn(Long.valueOf(1));
        when(resultSet.getString("offense")).thenReturn("Vaping");
        when(resultSet.getString("type")).thenReturn("Major Offense");
        when(resultSet.getString("description")).thenReturn("Bringing vape");

        OffenseDao dao = new OffenseDaoImpl();
        Offense offense = dao.findOffenseById("OFF-001");

        assertEquals(1, offense.getOffenseId());
        assertEquals("Vaping", offense.getOffense());
        assertEquals("Major Offense", offense.getType());
        assertEquals("Bringing vape", offense.getDescription());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "OFF-001");
        verify(preparedStatement, times(1)).executeQuery();
    }
    @Test
    void TestfindAllOffenseName() throws SQLException{
        Mockito.when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        Mockito.when(this.resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(this.resultSet.getString("offense")).thenReturn("Bullying").thenReturn("Tardiness");

        OffenseDao dao = new OffenseDaoImpl();
        List<String> offense = dao.findAllOffense();
        List<String> expectOffense = new ArrayList<>();
        expectOffense.add("Bullying");
        expectOffense.add("Tardiness");

        Assertions.assertEquals(expectOffense,offense);
        Mockito.verify(this.connection).prepareStatement(Mockito.anyString());
        Mockito.verify(this.preparedStatement).executeQuery();

    }
}