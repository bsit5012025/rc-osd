package org.rocs.osd.facade.employee.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rocs.osd.data.dao.employee.EmployeeDao;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.employee.EmployeeFacade;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.facade.record.impl.RecordFacadeImpl;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeFacadeImplTest {

    @Mock
    private EmployeeDao employeeDao;

    private Employee employee;

    private EmployeeFacade employeeFacade;

    @BeforeEach
    public void setUp() {
        employeeFacade = new EmployeeFacadeImpl(employeeDao);
    }

    @Test
    void testGetEmployeeByEmployeeID()
    {
        employee = new Employee();
        employee.setEmployeeId("EMP-001");
        employee.setFirstName("firstname");
        employee.setLastName("lastname");

        when(employeeDao.findEmployeeByEmployeeID("EMP-001")).thenReturn(employee);

        Employee employee1 = employeeFacade.getEmployeeByEmployeeID("EMP-001");

        assertNotNull(employee1);
        assertEquals("EMP-001", employee1.getEmployeeId());
        assertEquals("firstname", employee1.getFirstName());
        assertEquals("lastname", employee1.getLastName());

        verify(employeeDao, times(1)).findEmployeeByEmployeeID("EMP-001");
    }
}