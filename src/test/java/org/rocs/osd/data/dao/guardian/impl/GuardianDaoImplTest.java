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
import org.rocs.osd.model.person.guardian.Relationship;
import org.rocs.osd.model.person.studentguardian.StudentGuardian;

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

    private GuardianDao guardianDao;

    @BeforeEach
    public void setUp() throws Exception {

        connectionHelper = Mockito.mockStatic(ConnectionHelper.class);
        connectionHelper.when(ConnectionHelper::getConnection).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        guardianDao = new GuardianDaoImpl();
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
                .thenReturn(1L, 2L);
        when(resultSet.getString("relationship"))
                .thenReturn("Father", "Mother");
        when(resultSet.getString("studentID"))
                .thenReturn("JHS-0001", "JHS-0001");
        when(resultSet.getString("firstName"))
                .thenReturn("Carl Justine", "Carl Justine");
        when(resultSet.getString("lastName"))
                .thenReturn("Cain", "Cain");
        when(resultSet.getString("contactNumber"))
                .thenReturn("09171234567", "09181234567");

        List<StudentGuardian> list = guardianDao.findGuardianByStudentId("JHS-0001");

        assertNotNull(list);
        assertEquals(2, list.size());

        StudentGuardian sg1 = list.get(0);
        assertEquals("JHS-0001", sg1.getStudent().getStudentId());
        assertEquals(1L, sg1.getGuardian().getGuardianID());
        assertEquals(Relationship.FATHER, sg1.getGuardian().getRelationship());

        StudentGuardian sg2 = list.get(1);
        assertEquals("JHS-0001", sg2.getStudent().getStudentId());
        assertEquals(2L, sg2.getGuardian().getGuardianID());
        assertEquals(Relationship.MOTHER, sg2.getGuardian().getRelationship());
    }
}