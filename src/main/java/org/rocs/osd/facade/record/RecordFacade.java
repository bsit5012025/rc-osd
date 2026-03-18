package org.rocs.osd.facade.record;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RecordFacade
{
    boolean createStudentRecord(long enrollmentID, String employeeID,
                                long offenseID, Date dateOfViolation, long  actionID,
                                String remarks);
    boolean updateStudentRecord(Long recordID, Enrollment enrollment, Employee employee, Offense offense,
                                Date dateOfViolation, DisciplinaryAction action,
                                String remarks, RecordStatus status);
    boolean resolveRecord(Record record);
    Map<String, Double> getMostFrequentOffense(String schoolYear);
    int getTodayViolations();
    int getTotalViolations(String schoolYear);
    List<Record> getViolationsByDepartment(Department department, String schoolYear);
}

