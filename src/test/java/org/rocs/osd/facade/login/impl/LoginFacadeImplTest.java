package org.rocs.osd.facade.login.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.model.login.Login;
import org.rocs.osd.model.person.Person;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginFacadeImplTest
{
    @Mock
    private LoginDao loginDao;

    private Login login;

    private LoginFacade loginFacade;

    @BeforeEach
    public void setUp()
    {
        Person person = new Person();

        loginFacade = new LoginFacadeImpl(loginDao);
        login = new Login(1, "user","1234", person);
    }

    @Test
    void testLogin()
    {
        when(loginDao.findLoginByUsername("user")).thenReturn(login);

        boolean result = loginFacade.login("user", "1234");

        assertTrue(result);
        verify(loginDao, times(1)).findLoginByUsername("user");
    }

    @Test
    void testLoginInvalidPassword() {
        when(loginDao.findLoginByUsername("user")).thenReturn(login);

        assertFalse(loginFacade.login("user", "wrong"));
    }

    @Test
    void testLoginNonexistentUser() {
        when(loginDao.findLoginByUsername("unknown")).thenReturn(null);

        assertFalse(loginFacade.login("unknown", "1234"));
    }

    @Test
    void testLoginBlankUsernameOrPassword() {
        assertFalse(loginFacade.login("", "1234"));
        assertFalse(loginFacade.login("user", ""));
        assertFalse(loginFacade.login("", ""));
        assertFalse(loginFacade.login(null, null));
    }
}
