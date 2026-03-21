package org.rocs.osd.model.person.employee;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.person.Person;

/**
 * Represents an employee in the OSD System.
 * Inherits from Person and includes employee ID, department, and role.
 */
public class Employee extends Person {

    /** Unique employee ID. */
    private String employeeId;

    /** Department the employee belongs to. */
    private Department department;

    /** Role of the employee. */
    private String employeeRole;

    /** Default constructor. */
    public Employee() {
    }

    /**
     * Constructor with all fields.
     * @param personID ID inherited from Person.
     * @param lastName employee last name.
     * @param firstName employee first name.
     * @param middleName employee middle name.
     * @param employeeId unique employee ID.
     * @param department employee department.
     * @param employeeRole role of the employee.
     */
    public Employee(Long personID, String lastName,
                    String firstName, String middleName,
                    String employeeId, Department department,
                    String employeeRole) {
        super(personID, lastName, firstName, middleName);
        this.employeeId = employeeId;
        this.department = department;
        this.employeeRole = employeeRole;
    }

    /** @return unique employee ID. */
    public String getEmployeeId() {
        return employeeId;
    }

    /** @param employeeId sets the unique employee ID. */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /** @return the department of the employee. */
    public Department getDepartment() {
        return department;
    }

    /** @param department sets the department of the employee. */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /** @return the role of the employee. */
    public String getEmployeeRole() {
        return employeeRole;
    }

    /** @param employeeRole sets the role of the employee. */
    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}