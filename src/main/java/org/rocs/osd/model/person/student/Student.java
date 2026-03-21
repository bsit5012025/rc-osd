package org.rocs.osd.model.person.student;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.person.Person;

/**
 * Represents a student in the Office of Student Discipline System.
 * Stores personal info from Person plus student-specific details such as ID,
 * address, type, and department.
 */
public class Student extends Person {

    /** Unique identifier assigned to the student. */
    private String studentId;
    /** Unique identifier assigned to the student. */
    private String address;
    /** Type or category of the student. */
    private String studentType;
    /** Type or category of the student. */
    private Department department;

    /**
     * Default constructor.
     */
    public Student() {
    }

    /**
     * Constructs a Student with all details.
     *
     * @param personID ID from the Person class.
     * @param lastName last name of the student.
     * @param firstName first name of the student.
     * @param middleName middle name of the student.
     * @param studentId unique student ID.
     * @param address student's address.
     * @param studentType type/category of student.
     * @param department department of the student.
     */
    public Student(Long personID, String lastName,
                   String firstName, String middleName,
                   String studentId, String address,
                   String studentType, Department department) {
        super(personID, lastName, firstName, middleName);
        this.studentId = studentId;
        this.address = address;
        this.studentType = studentType;
        this.department = department;
    }

    /** @return the student ID. */
    public String getStudentId() {
        return studentId;
    }

    /** @param studentId sets the student ID. */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /** @return the student's address. */
    public String getAddress() {
        return address;
    }

    /** @param address sets the student's address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return the type of student. */
    public String getStudentType() {
        return studentType;
    }

    /** @param studentType sets the type of student. */
    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    /** @return the department of the student. */
    public Department getDepartment() {
        return department;
    }

    /** @param department sets the department of the student. */
    public void setDepartment(Department department) {
        this.department = department;
    }
}