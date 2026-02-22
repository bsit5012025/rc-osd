package org.rocs.osd.facade.Record;

import java.sql.Date;

public interface RecordFacade
{
    boolean createStudentRecord(long enrollmentID, String employeeID,
                                long offenseID, Date dateOfViolation, long  actionID,
                                String remarks);
}
