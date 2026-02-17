package org.rocs.osd.data.dao.record;

import org.rocs.osd.model.record.Record;

import java.sql.Date;
import java.util.List;

public interface RecordDao
{
    List<Record> findStudentByIdAndEnrolment(String studentID, String schoolYear, String studentLevel);
    boolean addStudentRecord(long enrollmentID, String employeeID,
                             long offenseID, Date dateOfViolation, long actionID,
                             String remarks, String status);
    boolean updateRecord(long recordID, String status, long OffenseID,
                         String remarks);

    boolean updateExistingDateOfViolationRecord(long recordID, Date dateOfViolation);
    boolean updateExistingDateOfResolutionRecord(long recordID, Date dateOfResolution);
}
