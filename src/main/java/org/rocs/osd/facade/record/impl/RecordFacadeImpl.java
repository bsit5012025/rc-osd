package org.rocs.osd.facade.record.impl;

import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.facade.record.RecordFacade;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Facade implementation for managing student records in the
 * Office of Student Discipline System.
 *
 * Provides methods to create, update, resolve records
 * and generate offense statistics.
 */
public class RecordFacadeImpl implements RecordFacade {

    /** Data access object for student records. */
    private RecordDao recordDao;

    /**
     * Constructor to inject the RecordDao dependency.
     *
     * @param pRecordDao the RecordDao instance to use.
     */
    public RecordFacadeImpl(RecordDao pRecordDao) {
        this.recordDao = pRecordDao;
    }

    /**
     * Creates a new student record in the system.
     *
     * @param enrollmentID the student's enrollment ID.
     * @param employeeID the ID of the employee recording the violation.
     * @param offenseID the ID of the offense committed.
     * @param dateOfViolation the date when the violation occurred.
     * @param actionID the ID of the disciplinary action applied.
     * @param remarks optional remarks.
     * @return true if the record was successfully
     * created, false otherwise.
     */
    @Override
    public boolean createStudentRecord(long enrollmentID, String employeeID,
                                       long offenseID, Date dateOfViolation,
                                       long actionID, String remarks) {
        if (employeeID == null || dateOfViolation == null) {
            return false;
        }

        if (remarks != null && remarks.length() > 500) {
            return false;
        }

        return recordDao.addStudentRecord(
                enrollmentID, employeeID, offenseID, dateOfViolation,
                actionID, remarks, RecordStatus.PENDING
        );
    }

    /**
     * Updates an existing student record.
     *
     * @param recordID the record's unique ID.
     * @param enrollment the enrollment details of the student.
     * @param employee the employee recording the violation.
     * @param offense the offense details.
     * @param dateOfViolation the date of the violation.
     * @param action the disciplinary action applied.
     * @param remarks optional remarks.
     * @param status the new status of the record.
     * @return true if the record was successfully
     * updated, false otherwise.
     */
    @Override
    public boolean updateStudentRecord(Long recordID, Enrollment enrollment,
                                       Employee employee, Offense offense,
                                       Date dateOfViolation,
                                       DisciplinaryAction action,
                                       String remarks, RecordStatus status) {
        if (employee.getEmployeeId() == null || dateOfViolation == null) {
            return false;
        }

        if (remarks != null && remarks.length() > 500) {
            return false;
        }

        Record record = new Record();
        record.setRecordId(recordID);
        record.setEnrollment(enrollment);
        record.setEmployee(employee);
        record.setOffense(offense);
        record.setDateOfViolation(dateOfViolation);
        record.setAction(action);
        record.setRemarks(remarks);
        record.setStatus(status);

        return recordDao.updateRecord(record);
    }

    /**
     * Resolves a student record by setting its status.
     * to RESOLVED and updating the resolution date.
     *
     * @param record the record to resolve.
     * @return true if the record was successfully
     * updated, false otherwise.
     */
    @Override
    public boolean resolveRecord(Record record) {
        record.setStatus(RecordStatus.RESOLVED);
        record.setDateOfResolution(new Date());
        return recordDao.updateRecord(record);
    }

    /**
     * Returns the most frequent offenses for a given
     * school year as percentages.
     *
     * @param schoolYear the school year to analyze.
     * @return a map of offense names and their
     * percentage of total violations.
     */
    @Override
    public Map<String, Double> getMostFrequentOffense(String schoolYear) {
        Map<String, Integer> offenses = recordDao.findMostFrequentOffenses(
                schoolYear
        );
        int totalViolations = recordDao.findTotalViolations(schoolYear);

        Map<String, Double> result = new LinkedHashMap<>();
        if (totalViolations == 0) {
            return result;
        }

        int count = 0;
        for (Map.Entry<String, Integer> entry : offenses.entrySet()) {
            if (count == 5) {
                break;
            }
            String offense = entry.getKey();
            int offenseCount = entry.getValue();
            double percentage = (offenseCount * 100.0) / totalViolations;
            result.put(offense, percentage);
            count++;
        }

        return result;
    }

    /**
     * Returns the number of violations recorded today.
     *
     * @return count of today's violations.
     */
    @Override
    public int getTodayViolations() {
        return recordDao.findTodayViolations();
    }

    /**
     * Returns the total number of violations in a given school year.
     *
     * @param schoolYear the school year to check
     * @return total violations count
     */
    @Override
    public int getTotalViolations(String schoolYear) {
        return recordDao.findTotalViolations(schoolYear);
    }

    /**
     * Retrieves student records filtered by department and school year.
     *
     * @param department the department to filter by.
     * @param schoolYear the school year to filter by.
     * @return list of records matching the criteria.
     */
    @Override
    public List<Record> getViolationsByDepartment(Department department,
                                                  String schoolYear) {
        return recordDao.findRecordListByDepartment(department, schoolYear);
    }

    @Override
    public List<Record> getRecordByStudentId(String studentId) {
        return recordDao.findRecordByStudentId(studentId);
    }
}
