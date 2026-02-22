package org.rocs.osd.data.dao.record;

import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.sql.Date;
import java.util.List;

public interface RecordDao
{
    List<Record> findStudentByIdAndEnrolment(String studentID, String schoolYear, String studentLevel);
    boolean addStudentRecord(long enrollmentID, String employeeID,
                             long offenseID, Date dateOfViolation, long actionID,
                             String remarks, RecordStatus status);
    boolean updateStudentRecordStatusById(long recordID, String status);
}
