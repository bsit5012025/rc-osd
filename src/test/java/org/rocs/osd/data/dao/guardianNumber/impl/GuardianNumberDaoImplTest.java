package org.rocs.osd.data.dao.guardianNumber.impl;

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
import org.rocs.osd.data.dao.guardianNumber.GuardianNumberDao;
import org.rocs.osd.model.person.guardian.Guardian;

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
    void testGetGuardiansByStudentID() throws Exception {

        when(preparedStatement.executeQuery()).thenReturn(resultSet);


        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getString("guardianID"))
                .thenReturn("G001")
                .thenReturn("G002");

        when(resultSet.getString("contactnumber"))
                .thenReturn("09171234567")
                .thenReturn("09181234567");

        when(resultSet.getString("relationship"))
                .thenReturn("Father")
                .thenReturn("Mother");

        when(resultSet.getString("studentID"))
                .thenReturn("JHS-0001")
                .thenReturn("JHS-0001");

        GuardianNumberDao dao = new GuardianNumberDaoImpl();

        List<Guardian> guardians = dao.getGuardiansByStudentID("JHS-0001");

        assertFalse(guardians.isEmpty());
        assertEquals(2, guardians.size());

        Guardian g1 = guardians.getFirst();

        assertEquals("G001", g1.getGuardianID());
        assertEquals("09171234567", g1.getContactNumber());
        assertEquals("Father", g1.getRelationship());
        assertEquals("JHS-0001", g1.getStudentID());

        Guardian g2 = guardians.get(1);

        assertEquals("G002", g2.getGuardianID());
        assertEquals("09181234567", g2.getContactNumber());
        assertEquals("Mother", g2.getRelationship());
        assertEquals("JHS-0001", g2.getStudentID());

        verify(preparedStatement).executeQuery();
    }
}