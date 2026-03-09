package org.rocs.osd.model.department;

/**
 * Represents a Department entity in the Office of Student Discipline System.
 * Contains the department ID and department name.
 */

public class Department {
    private long departmentId;
    private String departmentName;

    /**
     * Default constructor.
     * Initializes an empty Department object.
     */
    public Department() {
    }

    /**
     * Constructor to create a Department with ID and name.
     * @param departmentId   the ID of the department.
     * @param departmentName the name of the department.
     */
    public Department(long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    /**
     * @return the unique department ID.
     */
    public long getDepartmentId() {
        return departmentId;
    }

    /**
     *	@param departmentId sets the unique department ID.
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the name of the department.
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     *	@param departmentName sets the name of the department.
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
