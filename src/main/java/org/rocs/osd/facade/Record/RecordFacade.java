package org.rocs.osd.facade.Record;

import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.record.RecordStatus;

import java.sql.Date;

public interface RecordFacade
{
    boolean createStudentRecord(long enrollmentID, String employeeID,
                                long offenseID, Date dateOfViolation, long  actionID,
                                String remarks);
    boolean updateStudentRecord(Enrollment enrollment, Employee employee, Offense offense,
                                Date dateOfViolation, DisciplinaryAction action,
                                String remarks, RecordStatus status);
}

