package org.rocs.osd.data.dao.guardian.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.guardian.GuardianDao;
import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.guardian.Relationship;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardianDaoImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private static MockedStatic<ConnectionHelper> connectionHelper;

    @BeforeEach
    public void setUp() throws Exception {

        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @AfterEach
    public void tearDown() {
        connectionHelper.close();
    }

    @Test
    void testFindGuardiansByStudentID() throws Exception {

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next())
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        when(resultSet.getLong("guardianID"))
                .thenReturn(1L)
                .thenReturn(2L);

        when(resultSet.getString("contactNumber"))
                .thenReturn("09171234567")
                .thenReturn("09181234567");

        when(resultSet.getString("relationship"))
                .thenReturn("FATHER")
                .thenReturn("MOTHER");

        GuardianDao guardianDao = new GuardianDaoImpl();

        List<Guardian> guardians =
                guardianDao.findGuardianByStudentId("JHS-0001");

        assertEquals(2, guardians.size());

        Guardian g1 = guardians.get(0);

        assertEquals(1L, g1.getGuardianID());
        assertEquals("09171234567", g1.getContactNumber());
        assertEquals(Relationship.FATHER, g1.getRelationship());
    }
}