package org.rocs.osd.model.person.employee;

import org.rocs.osd.model.person.Person;

/**
 * Represents an employee with personal and employee-specific details
 */
public class Employee extends Person {
    /**
     * Unique employee identifier
     */
    private String employeeId;
    /**
     * Department ID of the employee
     */
    private String departmentId;
    /**
     * Role of the employee
     */
    private String employeeRole;

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * @param personID     unique ID from the Person class
     * @param lastName     employee's last name
     * @param firstName    employee's first name
     * @param middleName   employee's middle name
     * @param employeeId   unique employee ID
     * @param departmentId department ID of the employee
     * @param employeeRole role of the employee
     */
    public Employee(Long personID, String lastName, String firstName, String middleName, String employeeId, String departmentId, String employeeRole) {
        super(personID, lastName, firstName, middleName);
        this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.employeeRole = employeeRole;
    }

    /**
     * Gets the unique employee ID
     * @return employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Sets the unique employee ID
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * Gets the department ID of the employee
     * @return departmentId
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the department ID of the employee
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Gets the role of the employee
     *
     * @return employeeRole
     */
    public String getEmployeeRole() {
        return employeeRole;
    }

    /**
     * Sets the role or position of the employee
     * @param employeeRole the employeeRole to set
     */
    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}
