package org.rocs.osd.data.dao.disciplinary.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.disciplinary.action.DisciplinaryActionDao;
import org.rocs.osd.data.dao.disciplinary.action.impl.DisciplinaryActionImpl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DisciplinaryActionImplTest {

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
    void testFindActionByActionIdReturnAction()throws SQLException{
        when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        when(this.resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(this.resultSet.getString("action")).thenReturn("Community Service");

        DisciplinaryActionDao dao = new DisciplinaryActionImpl();
        String action = dao.findActionById(1L);

        assertNotNull(action);
        assertEquals("Community Service", action);

    }
    @Test
    void testFindAllActionsReturnListOfAction()throws SQLException{
        when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        when(this.resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(this.resultSet.getString("action"))
                .thenReturn("Community Service")
                .thenReturn("Probation");

        DisciplinaryActionDao dao = new DisciplinaryActionImpl();
        List<String> actions = dao.findAllAction();

        assertNotNull(actions);
        assertEquals(2, actions.size());
        assertEquals("Community Service", actions.get(0));
        assertEquals("Probation", actions.get(1));
    }
    @Test
    void testFindActionIdByActionReturnActionId()throws SQLException{
        when(this.preparedStatement.executeQuery()).thenReturn(this.resultSet);
        when(this.resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(this.resultSet.getLong("actionId")).thenReturn(1L);

        DisciplinaryActionDao dao = new DisciplinaryActionImpl();
        long actionId = dao.findActionIdByName("Community Service");

        assertEquals(1L, actionId);

    }
}