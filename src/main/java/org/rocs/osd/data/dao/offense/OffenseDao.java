package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.person.student.Student;

import java.sql.Date;
import java.util.ArrayList;

public interface OffenseDao
{
    Student getStudentById(String studentID);
    Student getStudentByName(String lastName, String firstName, String middleName);
    ArrayList<Record> getStudentRecord(String studentID);
    Offense getStudentOffense(String offenseID);
    boolean addStudentViolation(String recordID, String enrollmentID, String prefectID,
                                String offenseID, Date dateOfViolation, String actionID,
                                String remarks, String status);
}
