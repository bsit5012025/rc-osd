package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.person.student.Student;

import java.sql.Date;
import java.util.ArrayList;

public interface OffenseDao
{
    ArrayList<Student>getAllStudent();
    ArrayList<Student>getStudentByDepartmentID(long departmentID);
    Student getStudentById(String studentID);
    Student getStudentByName(String lastName, String firstName, String middleName);
    ArrayList<Record> getStudentRecord(String studentID);
    Offense getStudentOffense(String offenseID);
    boolean addStudentViolation(long enrollmentID, String employeeID,
                                long offenseID, Date dateOfViolation, long actionID,
                                String remarks, String status);
}
