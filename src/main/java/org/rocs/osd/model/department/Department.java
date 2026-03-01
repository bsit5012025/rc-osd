package org.rocs.osd.model.department;

/**
 * Represents a Department entity.
 * This class contains the department ID and department name.
 */
public class Department {
    private long departmentId;
    private String departmentName;

    /**
     * Default constructor.
     */
    public Department() {
    }

    /**
     * Constructor to create a Department with ID and name.
     * @param departmentId   the ID of the department.
     * @param departmentName the name of the department.
     */
    public Department(long departmentId, String departmentName) {

        // Department ID
        this.departmentId = departmentId;
        // Department Name
        this.departmentName = departmentName;
    }

    /**
     *  This will get the Department ID
     *  return the department ID
     */

    public long getDepartmentId() {
        return departmentId;
    }

    /**
     *   This will set the Department ID
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * This will get the Department Name
     * return the department ID
      */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * This will set the Department Name
      */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
