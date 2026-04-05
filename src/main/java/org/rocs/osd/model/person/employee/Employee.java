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
        // Intentionally empty constructor for employee model
    }

    /**
     * Constructor with all fields.
     * @param pPersonID ID inherited from Person.
     * @param pLastName employee last name.
     * @param pFirstName employee first name.
     * @param middleName employee middle name.
     * @param pEmployeeId unique employee ID.
     * @param pDepartment employee pDepartment.
     * @param pEmployeeRole role of the employee.
     */
    public Employee(Long pPersonID, String pLastName,
                    String pFirstName, String middleName,
                    String pEmployeeId, Department pDepartment,
                    String pEmployeeRole) {
        super(pPersonID, pLastName, pFirstName, middleName);
        this.employeeId = pEmployeeId;
        this.department = pDepartment;
        this.employeeRole = pEmployeeRole;
    }

    /** @return unique employee ID. */
    public String getEmployeeId() {
        return employeeId;
    }

    /** @param pEmployeeId sets the unique employee ID. */
    public void setEmployeeId(String pEmployeeId) {
        this.employeeId = pEmployeeId;
    }

    /** @return the department of the employee. */
    public Department getDepartment() {
        return department;
    }

    /** @param pDepartment sets the department of the employee. */
    public void setDepartment(Department pDepartment) {
        this.department = pDepartment;
    }

    /** @return the role of the employee. */
    public String getEmployeeRole() {
        return employeeRole;
    }

    /** @param pEmployeeRole sets the role of the employee. */
    public void setEmployeeRole(String pEmployeeRole) {
        this.employeeRole = pEmployeeRole;
    }
}
