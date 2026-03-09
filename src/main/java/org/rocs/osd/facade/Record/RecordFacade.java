package org.rocs.osd.facade.Record;

import org.rocs.osd.model.record.RecordStatus;

import java.sql.Date;

public interface RecordFacade
{
    boolean createStudentRecord(long enrollmentID, String employeeID,
                                long offenseID, Date dateOfViolation, long  actionID,
                                String remarks);
    boolean updateStudentRecord(long enrollmentId, String employeeId, long offenseId,
                                Date dateOfViolation, long actionId,
                                String remarks, RecordStatus status);
}

