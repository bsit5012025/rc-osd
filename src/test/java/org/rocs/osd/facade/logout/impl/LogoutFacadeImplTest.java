package org.rocs.osd.facade.login.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.facade.login.LoginFacade;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LogoutFacadeImplTest {

    private LoginFacade loginFacade;

    @BeforeEach
    public void setUp() {
        loginFacade = new LoginFacadeImpl(null);
    }

    @Test
    void testLogoutSuccess() {
        boolean result = loginFacade.logout();

        assertTrue(result);
    }

    @Test
    void testLogoutMultipleCalls() {
        assertTrue(loginFacade.logout());
        assertTrue(loginFacade.logout());
    }
}