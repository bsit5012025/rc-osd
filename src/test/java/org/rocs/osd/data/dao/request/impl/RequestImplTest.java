package org.rocs.osd.data.dao.request.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RequestDaoImplTest {

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
    void testGetAllRequests() throws Exception {

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getLong("requestID")).thenReturn(1L);
        when(resultSet.getString("employeeID")).thenReturn("EMP-001");
        when(resultSet.getString("details")).thenReturn("St. Andrew");
        when(resultSet.getString("type")).thenReturn("By Section");
        when(resultSet.getString("message"))
                .thenReturn("Requesting for the conduct record of all students in St. Andrew");
        when(resultSet.getString("status")).thenReturn("APPROVED");

        RequestDao dao = new RequestDaoImpl();
        List<Request> requests = dao.getAllRequests();

        assertFalse(requests.isEmpty());

        Request r = requests.getFirst();

        assertEquals(1, r.getRequestID());
        assertNotNull(r.getEmployee());
        assertEquals("EMP-001", r.getEmployee().getEmployeeId());
        assertEquals("St. Andrew", r.getDetails());
        assertEquals("By Section", r.getType());
        assertEquals(
                "Requesting for the conduct record of all students in St. Andrew",
                r.getMessage()
        );
        assertEquals(RequestStatus.APPROVED, r.getStatus());

        verify(preparedStatement).executeQuery();
    }

    @Test
    void testSetRequest() throws Exception {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        RequestDao dao = new RequestDaoImpl();

        Employee emp = new Employee();
        emp.setEmployeeId("EMP-001");

        dao.setRequest(
                emp,
                "St. Andrew",
                "Requesting for the conduct record of all students in St. Andrew",
                "By Section"
        );

        verify(connection, times(1)).prepareStatement(anyString());


        verify(preparedStatement).setString(1, "EMP-001");
        verify(preparedStatement).setString(2, "St. Andrew");
        verify(preparedStatement).setString(3, "By Section");
        verify(preparedStatement).setString(4, "Requesting for the conduct record of all students in St. Andrew");
        verify(preparedStatement).setString(5, "PENDING");

        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testUpdateRequestStatus() throws Exception {

        when(preparedStatement.executeUpdate()).thenReturn(1);

        RequestDao dao = new RequestDaoImpl();

        boolean status = dao.updateRequestStatus(1, RequestStatus.APPROVED);

        assertTrue(status);

        verify(connection, times(1)).prepareStatement(anyString());

        verify(preparedStatement).setString(1, "APPROVED");
        verify(preparedStatement).setString(2, "1");

        verify(preparedStatement).executeUpdate();
    }
}