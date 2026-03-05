package org.rocs.osd.facade.Record.impl;

import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.Record.RecordFacade;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

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
        if(remarks == null)
        {
            remarks = "";
        }

        if(employeeID == null || dateOfViolation == null || remarks.length() > 500)
        {
            return false;
        }

        boolean savedSuccessfully =  recordDao.addStudentRecord(enrollmentID,
                employeeID, offenseID, dateOfViolation, actionID, remarks,
                RecordStatus.PENDING);

        return savedSuccessfully;
    }

    @Override
    public boolean updateStudentRecord(long enrollmentId, String employeeId, long offenseId,
                                       Date dateOfViolation, long actionId,
                                       String remarks, RecordStatus status)
    {
        if(remarks == null)
        {
            remarks = "";
        }

        if(employeeId == null || dateOfViolation == null || remarks.length() > 500)
        {
            return false;
        }

        Record record = new Record();
        record.setEnrollmentId(enrollmentId);
        record.setEmployeeId(employeeId);
        record.setOffenseId(offenseId);
        record.setDateOfViolation(dateOfViolation);
        record.setActionId(actionId);
        record.setRemarks(remarks);
        record.setStatus(status);
        boolean savedSuccessfully = recordDao.updateRecord(record);

        return savedSuccessfully;
    }

}
