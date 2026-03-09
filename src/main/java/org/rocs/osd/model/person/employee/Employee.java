package org.rocs.osd.model.person.employee;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.person.Person;

/**
 * Represents an employee with personal and employee-specific details.
 * Inherits from Person and includes employee ID, department, and role.
 */
public class Employee extends Person {

    private String employeeId;
    private Department department;
    private String employeeRole;

    /**
     * Default constructor.
     * Initializes an empty Employee object.
     */
    public Employee() {
    }


    public Employee(Long personID, String lastName, String firstName, String middleName, String employeeId, Department department, String employeeRole) {
        super(personID, lastName, firstName, middleName);
        this.employeeId = employeeId;
        this.department = department;
        this.employeeRole = employeeRole;
    }

    /**
     * @return the unique employee ID.
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     *	@param employeeId sets the unique employee ID.
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * @return the role of the employee.
     */
    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}
