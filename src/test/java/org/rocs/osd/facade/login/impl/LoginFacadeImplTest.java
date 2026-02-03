package org.rocs.osd.facade.login.impl;

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
import org.rocs.osd.data.dao.login.impl.LoginDaoImpl;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.model.login.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginFacadeImplTest
{
    @Mock
    private LoginDao loginDao;

    private Login login;

    private LoginFacade loginFacade;

    @BeforeEach
    public void setUp() throws SQLException
    {
        loginFacade = new LoginFacadeImpl(loginDao);
        login = new Login(1, "user","1234");
    }

    @Test
    public void testLogin () throws SQLException
    {
        when(loginDao.findLoginByUsername("user")).thenReturn(login);

        boolean result = loginFacade.login("user", "1234");

        assert(result);
        verify(loginDao, times(1)).findLoginByUsername(anyString());
    }
}