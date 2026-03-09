package org.rocs.osd.facade.Record.impl;

import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.Record.RecordFacade;
import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
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

        if(employeeID == null || dateOfViolation == null)
        {
            return false;
        }

        if(remarks != null && remarks.length() > 500)
        {
            return false;
        }

        boolean savedSuccessfully =  recordDao.addStudentRecord(enrollmentID,
                employeeID, offenseID, dateOfViolation, actionID, remarks,
                RecordStatus.PENDING);

        return savedSuccessfully;
    }

    @Override
    public boolean updateStudentRecord(Enrollment enrollment, Employee employee, Offense offense,
                                       Date dateOfViolation, DisciplinaryAction action,
                                       String remarks, RecordStatus status)
    {
        if (employee.getEmployeeId() == null || dateOfViolation == null )
        {
            return false;
        }

        if(remarks != null && remarks.length() > 500)
        {
           return false;
        }

        Record record = new Record();
        record.setEnrollment(enrollment);
        record.setEmployee(employee);
        record.setOffense(offense);
        record.setDateOfViolation(dateOfViolation);
        record.setAction(action);
        record.setRemarks(remarks);
        record.setStatus(status);
        return recordDao.updateRecord(record);
    }

}
