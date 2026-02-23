package org.rocs.osd.data.dao.login.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.model.login.Login;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginDaoImplTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<Login> typedQuery;

    private LoginDaoImpl loginDao;

    @BeforeEach
    public void setUp() {
        loginDao = new LoginDaoImpl(em);
    }

    @Test
    public void testFindLoginByUsername() {
        String username = "admin";
        Login expectedLogin = new Login();
        expectedLogin.setUsername(username);

        when(em.createQuery(anyString(), eq(Login.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter("username", username)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(expectedLogin);

        Login result = loginDao.findByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());

        verify(em).createQuery(contains("WHERE login.username = :username"), eq(Login.class));
        verify(typedQuery).setParameter("username", username);
    }
}