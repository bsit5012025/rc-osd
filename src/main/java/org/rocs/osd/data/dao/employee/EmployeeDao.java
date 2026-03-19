package org.rocs.osd.data.dao.employee;

import org.rocs.osd.model.person.employee.Employee;

public interface EmployeeDao
{
    Employee findEmployeeByEmployeeID(String employeeID);

}
