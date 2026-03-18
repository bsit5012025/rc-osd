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
import org.rocs.osd.model.person.studentGuardian.StudentGuardian;

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

        when(resultSet.getString("studentID"))
                .thenReturn("JHS-0001")
                .thenReturn("JHS-0001");

        GuardianDao dao = new GuardianDaoImpl();

        List<StudentGuardian> list = dao.findGuardianByStudentId("JHS-0001");

        assertEquals(2, list.size());

        StudentGuardian sg1 = list.get(0);
        assertEquals("JHS-0001", sg1.getStudent().getStudentId());
        assertEquals(1L, sg1.getGuardian().getGuardianID());
        assertEquals(Relationship.FATHER, sg1.getGuardian().getRelationship());
    }
}