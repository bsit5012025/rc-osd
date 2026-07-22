package org.rocs.osd.data.dao.enrollment;

import org.rocs.osd.model.enrollment.Enrollment;
import java.util.List;

/**
 * DAO interface for handling student enrollment data in the
 * Office of Student Discipline System.
 */
public interface EnrollmentDao {

    /**
     * Retrieves the latest enrollment ID for a given student.
     * @param studentId the student ID
     * @return the latest enrollment ID, or -1 if not found
     */
    long findEnrollmentIdByStudentId(String studentId);

    /**
     * Retrieves all enrollments for a specific student.
     * @param studentId the ID of the student
     * @return a list of Enrollment objects, sorted by school year
     *         in descending order. Returns an empty list if none found.
     */
    List<Enrollment> findEnrollmentsByStudentId(String studentId);

    /**
     * Retrieves the latest enrollment record for all students.
     * @return a list of Enrollment objects representing
     * the most recent enrollment of each student.
     */
    List<Enrollment> findAllLatestEnrollments();

    /**
     * Retrieves the enrollment information of a student based on the
     * specified student Grade level and full name.
     *
     * @param studentLevel the student's Grade level.
     * @param firstName the student's first name.
     * @param middleName the student's middle name.
     * @param lastName the student's last name.
     * @return the Matching enrollment, if
     *         no matching enrollment is found return null.
     */
    Enrollment findEnrollmentsByStudentLevelAndName(
            String studentLevel,
            String firstName,
            String middleName,
            String lastName
    );

    /**
     * Updates the disciplinary status of a
     * student's enrollment record in the database.
     *
     * @param statusID the new disciplinary status ID to be assigned.
     * @param studentID the unique identifier of the student.
     * @param schoolYear the school year of the enrollment record to update.
     * @return return true if query successfully update's, false if not.
     */
    boolean setDisciplinaryStatusID(
            long statusID, String studentID, String schoolYear);
}
