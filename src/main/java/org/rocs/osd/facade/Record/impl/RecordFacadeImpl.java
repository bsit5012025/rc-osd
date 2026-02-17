package org.rocs.osd.facade.Record.impl;

import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.Record.RecordFacade;

import java.sql.Date;

public class RecordFacadeImpl implements RecordFacade
{
    private RecordDao recordDao;

    public RecordFacadeImpl(RecordDao recordDao)
    {
        this.recordDao = recordDao;
    }

    @Override
    public boolean createStudentRecord(long enrollmentID, String employeeID,
                                       long offenseID, Date dateOfViolation, long actionID,
                                       String remarks)
    {
        if(remarks == null || employeeID == null
                || dateOfViolation == null || remarks.length() > 500)
        {
            return false;
        }
        String status = "Pending";

        return recordDao.addStudentRecord(enrollmentID, employeeID, offenseID, dateOfViolation,
                actionID, remarks, status);
    }
}
