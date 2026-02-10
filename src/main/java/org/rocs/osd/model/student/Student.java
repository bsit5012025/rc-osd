package org.rocs.osd.model.student;

import java.sql.Date;

public class Student
{
    // temporary
    // will be change later by zen on his task
    private String studentID;
    private String lastName;
    private String firstName;
    private String offenseType;
    private Date dateOfViolation;

    public Student() {
    }

    public Student(String studentID, String lastName, String firstName, String offenseType, Date dateOfViolation) {
        this.studentID = studentID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.offenseType = offenseType;
        this.dateOfViolation = dateOfViolation;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOffenseType() {
        return offenseType;
    }

    public void setOffenseType(String offenseType) {
        this.offenseType = offenseType;
    }

    public Date getDateOfViolation() {
        return dateOfViolation;
    }

    public void setDateOfViolation(Date dateOfViolation) {
        this.dateOfViolation = dateOfViolation;
    }

    public String getFullName()
    {
        return firstName+" "+lastName;
    }

}
