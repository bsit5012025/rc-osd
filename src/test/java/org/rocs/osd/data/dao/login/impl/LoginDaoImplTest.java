package org.rocs.osd.data.dao.login.impl;

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
import org.rocs.osd.model.login.Login;
import org.rocs.osd.model.person.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginDaoImplTest {

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
    public void testFindLoginByUsername () throws SQLException{
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("id")).thenReturn(Long.valueOf(1));
        when(resultSet.getString("username")).thenReturn("test");
        when(resultSet.getString("password")).thenReturn("1234");
        when(resultSet.getString("lastname")).thenReturn("LName");
        when(resultSet.getString("firstname")).thenReturn("FName");
        when(resultSet.getString("middleName")).thenReturn("MName");

        LoginDao dao = new LoginDaoImpl();
        Login login = dao.findLoginByUsername("test");
        Person person = login.getPerson();

        assertNotNull(login);
        assertEquals(Long.valueOf(1), login.getId());
        assertEquals("test", login.getUsername());
        assertEquals("1234", login.getPassword());
        assertEquals("LName", person.getLastName());
        assertEquals("FName", person.getFirstName());
        assertEquals("MName", person.getMiddleName());

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).setString(1, "test");
        verify(preparedStatement, times(1)).executeQuery();
    }

}