package org.rocs.osd.facade.employee.impl;

import org.rocs.osd.data.dao.employee.EmployeeDao;
import org.rocs.osd.facade.employee.EmployeeFacade;
import org.rocs.osd.model.person.employee.Employee;

public class EmployeeFacadeImpl implements EmployeeFacade {
    /** DAO for handling employee data operations. */
    private EmployeeDao employeeDao;

    /**
     * Constructs a facade with a custom employeeDao implementation.
     * @param dao the employeeDao to use for database operations
     */
    public EmployeeFacadeImpl(EmployeeDao dao) {
        this.employeeDao = dao;
    }

    /**
     * Constructs a facade with a custom employeeDao implementation.
     * @param employeeID the AppealDao to use for database operations
     * @return the object corresponding to the given ID,
     * or if no employee is found
     */
    @Override
    public Employee getEmployeeByEmployeeID(String employeeID) {
        return employeeDao.findEmployeeByEmployeeID(employeeID);
    }
}
