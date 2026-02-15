package org.rocs.osd.data.dao.offense;

import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;

import java.sql.Date;
import java.util.List;

public interface OffenseDao
{
    List<Record> getStudentRecord(String studentID);
    Offense getStudentOffense(String offenseID);
    boolean addStudentViolation(long enrollmentID, String employeeID,
                                long offenseID, Date dateOfViolation, long actionID,
                                String remarks, String status);
}
