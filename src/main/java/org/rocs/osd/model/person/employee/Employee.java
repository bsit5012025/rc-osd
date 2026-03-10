package org.rocs.osd.model.person.employee;

import org.rocs.osd.model.person.Person;

/**
 * Represents an employee with personal and employee-specific details.
 * Inherits from Person and includes employee ID, department, and role.
 */
public class Employee extends Person {

    private String employeeId;

    private String departmentId;

    private String employeeRole;

    /**
     * Default constructor.
     * Initializes an empty Employee object.
     */
    public Employee() {
    }

    /**
     * Constructor to create an Employee with personal and employee-specific details.
     * @param personID     unique ID from the Person class.
     * @param lastName     employee's last name.
     * @param firstName    employee's first name.
     * @param middleName   employee's middle name.
     * @param employeeId   unique employee ID.
     * @param departmentId department ID of the employee.
     * @param employeeRole role of the employee.
     */
    public Employee(Long personID, String lastName, String firstName, String middleName, String employeeId, String departmentId, String employeeRole) {
        super(personID, lastName, firstName, middleName);
        this.employeeId = employeeId;
        this.departmentId = departmentId;
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

    /**
     * @return the department ID of the employee.
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     *	@param departmentId sets the department ID of the employee.
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the role of the employee.
     */
    public String getEmployeeRole() {
        return employeeRole;
    }

    /**
     *	@param employeeRole sets the role of the employee.
     */
    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}
