package org.rocs.osd.model.department;

public class Department {
    private long departmentId;
    private String departmentName;

    public Department() {
    }

    public Department(long departmentId, String departmentName) {

        // Department ID
        this.departmentId = departmentId;
        // Department Name
        this.departmentName = departmentName;
    }

    // This will get the Department ID
    public long getDepartmentId() {
        return departmentId;
    }

    // This will set the Department ID
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    // This will get the Department Name
    public String getDepartmentName() {
        return departmentName;
    }

    // This will set the Department Name
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
