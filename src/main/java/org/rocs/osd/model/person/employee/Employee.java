package org.rocs.osd.model.person.employee;

import org.rocs.osd.model.person.Person;

public class Employee extends Person {
    private String employeeId;
    private String departmentId;
    private String employeeRole;

    public Employee() {
    }

    public Employee(Long personID, String lastName, String firstName, String middleName, String employeeId, String departmentId, String employeeRole) {
        super(personID, lastName, firstName, middleName);
        this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.employeeRole = employeeRole;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}
