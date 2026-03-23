package org.rocs.osd.data.dao.record;

import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object interface for managing student records.
 * Provides methods to retrieve, add, update, and analyze student records
 * in the Office of Student Discipline System.
 */
public interface RecordDao {

    /**
     * Retrieves all records for a given school year.
     * @param schoolYear the school year to filter records.
     * @return a list of student records for the specified year.
     */
    List<Record> findAllBySchoolYear(String schoolYear);

    /**
     * Adds a new student record to the database.
     * @param enrollmentID the ID of the student's enrollment.
     * @param employeeID the ID of the employee logging the record.
     * @param offenseID the ID of the offense committed.
     * @param dateOfViolation the date the violation occurred.
     * @param actionID the ID of the disciplinary action applied.
     * @param remarks additional notes or remarks.
     * @param status the status of the record.
     * @return true if the record was added successfully, false otherwise.
     */
    boolean addStudentRecord(long enrollmentID,
                             String employeeID,
                             long offenseID,
                             Date dateOfViolation,
                             long actionID,
                             String remarks,
                             RecordStatus status);

    /**
     * Updates an existing student record in the database.
     * @param record the record object containing updated information.
     * @return true if the update succeeded, false otherwise.
     */
    boolean updateRecord(Record record);

    /**
     * Retrieves all records for a specific department and school year.
     * @param department the department to filter records.
     * @param schoolYear the school year to filter records.
     * @return a list of records matching the criteria.
     */
    List<Record> findRecordListByDepartment(
            Department department, String schoolYear);

    /**
     * Counts the total number of violations for a given school year.
     * @param schoolYear the school year to count violations.
     * @return the total number of violations.
     */
    int findTotalViolations(String schoolYear);

    /**
     * Counts the total number of violations recorded today.
     * @return the total number of violations recorded today.
     */
    int findTodayViolations();

    /**
     * Retrieves the most frequent offenses in a given school year.
     * @param schoolYear the school year to analyze.
     * @return a map of offense names and their frequency counts.
     */
    Map<String, Integer> findMostFrequentOffenses(String schoolYear);
    /***
     * Retrieves record of specific student.
     * @param studentId the student ID to find record.
     * @return a list record of students.
     */
    List<Record> findRecordByStudentId(String studentId);

}
