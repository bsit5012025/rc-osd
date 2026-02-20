package org.rocs.osd.model.person.student;

import org.rocs.osd.model.person.Person;

public class Student extends Person {
    private String studentId;
    private String address;
    private String studentType;
    private long departmentId;

    public Student() {
    }

    public Student(Long personID, String lastName, String firstName, String middleName, String studentId, String address, String studentType, long departmentId) {
        super(personID, lastName, firstName, middleName);
        this.studentId = studentId;
        this.address = address;
        this.studentType = studentType;
        this.departmentId = departmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }
}
