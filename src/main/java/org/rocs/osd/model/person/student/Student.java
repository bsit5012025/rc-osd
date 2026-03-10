package org.rocs.osd.model.person.student;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.person.Person;

/**
 * Represents a student entity in the Office of Student Discipline System.
 * Stores personal information inherited from Person, as well as student-specific details such as ID, address, type, and department.
 */
    public class Student extends Person {

    private String studentId;
    private String address;
    private String studentType;
    private Department department;


    /**
     * Default constructor.
     */
    public Student() {
    }

    public Student(Long personID, String lastName, String firstName, String middleName, String studentId, String address, String studentType, Department department) {
        super(personID, lastName, firstName, middleName);
        this.studentId = studentId;
        this.address = address;
        this.studentType = studentType;
        this.department = department;
    }


    /**
     * Gets the student ID.
     *
     * @return  the studentId.
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets the student ID.
     *
     * @param studentId  the studentId to set.
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the student's address.
     *
     * @return  the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the student's address.
     *
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the type of student.
     *
     * @return  the studentType.
     */
    public String getStudentType() {
        return studentType;
    }

    /**
     * Sets the type of student.
     *
     * @param studentType  the studentType to set.
     */
    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
