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
        // Default constructor
    }

    /**
     * Constructs a Student with all details.
     *
     * @param pPersonID ID from the Person class.
     * @param pLastName last name of the student.
     * @param pFirstName first name of the student.
     * @param pMiddleName middle name of the student.
     * @param pStudentId unique student ID.
     * @param pAddress student's address.
     * @param pStudentType type/category of student.
     * @param pDepartment department of the student.
     */
    public Student(Long pPersonID, String pLastName,
                   String pFirstName, String pMiddleName,
                   String pStudentId, String pAddress,
                   String pStudentType, Department pDepartment) {
        super(pPersonID, pLastName, pFirstName, pMiddleName);
        this.studentId = pStudentId;
        this.address = pAddress;
        this.studentType = pStudentType;
        this.department = pDepartment;
    }

    /** @return the student ID. */
    public String getStudentId() {
        return studentId;
    }

    /** @param pStudentId sets the student ID. */
    public void setStudentId(String pStudentId) {
        this.studentId = pStudentId;
    }

    /** @return the student's address. */
    public String getAddress() {
        return address;
    }

    /** @param pAddress sets the student's pAddress. */
    public void setAddress(String pAddress) {
        this.address = pAddress;
    }

    /** @return the type of student. */
    public String getStudentType() {
        return studentType;
    }

    /** @param pStudentType sets the type of student. */
    public void setStudentType(String pStudentType) {
        this.studentType = pStudentType;
    }

    /** @return the department of the student. */
    public Department getDepartment() {
        return department;
    }

    /** @param pDepartment sets the department of the student. */
    public void setDepartment(Department pDepartment) {
        this.department = pDepartment;
    }
}
