package org.rocs.osd.data.dao.employee;

import org.rocs.osd.model.person.employee.Employee;

/**
 * DAO interface for managing Employee operations.
 * Provides methods to fetch Employee information from the data source.
 */
public interface EmployeeDao {

    /**
     * Finds the Data for info of an Employee by its unique ID.
     * @param employeeID the unique ID of the employee
     * @return the Employee object if found; null if not found
     */
    Employee findEmployeeByEmployeeID(String employeeID);
}
