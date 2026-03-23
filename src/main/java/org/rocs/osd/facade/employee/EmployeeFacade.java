package org.rocs.osd.facade.employee;

import org.rocs.osd.model.person.employee.Employee;

public interface EmployeeFacade {
    /**
     * Retrieves specific Employee info
     * by getting its employeeID.
     * @param employeeID the ID of the employee for searching
     * @return a List of pending Appeal objects
     */
    Employee getEmployeeByEmployeeID(String employeeID);
}
