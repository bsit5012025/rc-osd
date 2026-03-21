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

/**
 * Facade interface for managing student records in the Office of Student
 * Discipline System.
 *
 * Provides methods to create, update, resolve records
 * and retrieve statistics.
 */
public interface RecordFacade {

    /**
     * Creates a new student record in the system.
     *
     * @param enrollmentID the student's enrollment ID
     * @param employeeID the employee ID recording the violation
     * @param offenseID the ID of the offense
     * @param dateOfViolation the date the violation occurred
     * @param actionID the ID of the disciplinary action applied
     * @param remarks optional remarks (max 500 characters)
     * @return true if the record was created successfully
     */
    boolean createStudentRecord(long enrollmentID, String employeeID,
                                long offenseID, Date dateOfViolation,
                                long actionID, String remarks);

    /**
     * Updates an existing student record with new information.
     *
     * @param recordID the record's unique ID.
     * @param enrollment the student's enrollment details.
     * @param employee the employee recording the violation.
     * @param offense the offense details.
     * @param dateOfViolation the date of the violation.
     * @param action the disciplinary action applied.
     * @param remarks optional remarks.
     * @param status the new status of the record.
     * @return true if the record was updated successfully.
     */
    boolean updateStudentRecord(Long recordID, Enrollment enrollment,
                                Employee employee, Offense offense,
                                Date dateOfViolation, DisciplinaryAction action,
                                String remarks, RecordStatus status);

    /**
     * Resolves a student record by setting its status to RESOLVED and updating
     * the resolution date.
     *
     * @param record the record to resolve
     * @return true if the record was updated successfully
     */
    boolean resolveRecord(Record record);

    /**
     * Retrieves the most frequent offenses for a school year as percentages.
     *
     * @param schoolYear the school year to analyze
     * @return a map of offense names and their percentage of total violations
     */
    Map<String, Double> getMostFrequentOffense(String schoolYear);

    /**
     * Returns the number of violations recorded today.
     *
     * @return today's violation count
     */
    int getTodayViolations();

    /**
     * Returns the total number of violations in a given school year.
     *
     * @param schoolYear the school year to check
     * @return total number of violations
     */
    int getTotalViolations(String schoolYear);

    /**
     * Retrieves student records filtered by department and school year.
     *
     * @param department the department to filter by
     * @param schoolYear the school year to filter by
     * @return list of records matching the criteria
     */
    List<Record> getViolationsByDepartment(Department department,
                String schoolYear);
}