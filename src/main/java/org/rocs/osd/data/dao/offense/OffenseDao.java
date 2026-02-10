package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.student.Student;

import java.util.ArrayList;

public interface OffenseDao
{
    ArrayList<Student> getStudentById(String studentID);

    boolean addStudentViolation(String recordID, String enrollmentID, String prefectID,
                             String offenseID, String dateOfViolation, String actionID,
                             String remarks, String status);
}
