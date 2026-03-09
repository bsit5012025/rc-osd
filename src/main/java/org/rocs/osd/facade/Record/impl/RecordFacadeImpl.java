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

/**
 * Facade implementation for managing student records in the Office of Student Discipline System.
 */

public class RecordFacadeImpl implements RecordFacade
{
    private RecordDao recordDao;

    /**
     * Constructor to inject the RecordDao dependency.
     *
     * @param recordDao the RecordDao instance to use.
     */
    public RecordFacadeImpl(RecordDao recordDao)
    {
        this.recordDao = recordDao;
    }

    /**
     * Creates a new student record.
     *
     * @param enrollmentID the enrollment ID of the student.
     * @param employeeID the ID of the employee recording the offense.
     * @param offenseID the ID of the offense.
     * @param dateOfViolation the date the violation occurred.
     * @param actionID the ID of the disciplinary action taken.
     * @param remarks additional remarks (optional, max 500 characters).
     * @return true if the record was successfully created, false otherwise.
     */
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

    /**
     * Updates an existing student record.
     *
     * @param enrollmentId the enrollment ID of the student.
     * @param employeeId the ID of the employee recording the offense.
     * @param offenseId the ID of the offense.
     * @param dateOfViolation the date of violation.
     * @param actionId the ID of the disciplinary action.
     * @param remarks additional remarks (optional, max 500 characters).
     * @param status the current status of the record.
     * @return true if the record was successfully updated, false otherwise.
     */
    @Override
    public boolean updateStudentRecord(long enrollmentId, String employeeId, long offenseId,
                                       Date dateOfViolation, long actionId,
                                       String remarks, RecordStatus status)
    {
        if (employeeId == null || dateOfViolation == null )
        {
            return false;
        }

        if(remarks != null && remarks.length() > 500)
        {
           return false;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(enrollmentId);

        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        Offense offense = new Offense();
        offense.setOffenseId(offenseId);

        DisciplinaryAction action = new DisciplinaryAction();
        action.setActionId(actionId);

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
