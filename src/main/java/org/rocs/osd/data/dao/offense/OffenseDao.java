package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.login.Student;

import java.sql.Date;
import java.util.ArrayList;

public interface OffenseDao
{
    ArrayList<Student> getStudentById(String studentID);

    void addStudentViolation(String recordID, String enrollmentID, String prefectID,
                             String offenseID, String dateOfViolation, String actionID,
                             String remarks, String status);
}
