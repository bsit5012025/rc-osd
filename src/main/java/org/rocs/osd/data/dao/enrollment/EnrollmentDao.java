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
}
