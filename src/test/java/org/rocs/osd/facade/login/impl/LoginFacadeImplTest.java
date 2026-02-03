package org.rocs.osd.facade.login.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.model.login.Login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginFacadeImplTest
{
    @Mock
    private LoginDao loginDao;

    @Mock
    private LoginFacadeImpl loginFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize @Mock
        loginFacade = new LoginFacadeImpl(loginDao); // inject mock DAO
    }

    @Test
    public void testlogin ()
    {
        Login login = new Login();
        login.setPassword("123");

        when(loginDao.findLoginByUsername("Carl Justine Cain")).thenReturn(login);
        boolean result = loginFacade.login("Carl Justine Cain", "123");

        assertTrue(result);
    }
}