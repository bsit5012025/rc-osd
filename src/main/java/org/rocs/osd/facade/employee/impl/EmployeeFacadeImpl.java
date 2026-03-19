package org.rocs.osd.facade.employee.impl;

import org.rocs.osd.data.dao.employee.EmployeeDao;
import org.rocs.osd.facade.employee.EmployeeFacade;
import org.rocs.osd.model.person.employee.Employee;


public class EmployeeFacadeImpl implements EmployeeFacade
{
    private EmployeeDao employeeDao;

    public EmployeeFacadeImpl(EmployeeDao employeeDao)
    {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee getEmployeeByEmployeeID(String employeeID)
    {
        return employeeDao.findEmployeeByEmployeeID(employeeID);
    }
}
