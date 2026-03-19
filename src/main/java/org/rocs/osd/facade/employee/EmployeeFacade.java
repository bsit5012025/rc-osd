package org.rocs.osd.facade.employee;

import org.rocs.osd.model.person.employee.Employee;

public interface EmployeeFacade
{
    Employee getEmployeeByEmployeeID(String employeeID);
}
