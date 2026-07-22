package org.rocs.osd.facade.enrollment;

import org.rocs.osd.model.enrollment.Enrollment;

import java.util.List;

/**
 * Facade interface for Enrollment operations in the Office of Student
 * Discipline System.
 */
public interface EnrollmentFacade {

    /**
     * Retrieves a list of the latest enrollment records for all students.
     * @return a List of Enrollment objects representing the most recent
     * enrollment of each student.
     */
    List<Enrollment> getAllLatestEnrollments();

    /**
     * Retrieves the latest enrollment record of search students.
     *
     * @param studentInfo The id, name, year, or
     *                   section of the students.
     * @return a list of Enrollment objects representing.
     */
    List<Enrollment> getLatestEnrollmentByStudentInfo(String studentInfo);

    /**
     * Retrieves all enrollment records associated with a specific student.
     * @param studentId the unique ID of the student.
     * @return a List of Enrollment objects for the given student.
     */
    List<Enrollment> getEnrollmentsByStudentId(String studentId);

    /**
     * Retrieves the most recent enrollment record for a specific student.
     * @param studentId the unique ID of the student.
     * @return the latest Enrollment object for the student,
     * or null if none exist.
     */
    Enrollment getLatestEnrollmentByStudentId(String studentId);

}
