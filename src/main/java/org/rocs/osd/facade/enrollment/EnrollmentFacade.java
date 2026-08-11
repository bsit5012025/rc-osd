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

    /**
     * Retrieves the enrollment information of a student based on the
     * specified student level and full name.
     *
     * @param studentLevel the student's Grade level.
     * @param firstName the student's first name.
     * @param middleName the student's middle name.
     * @param lastName the student's last name.
     * @return the matching enrollment, if not return null.
     */
    Enrollment getEnrollmentsByStudentLevelAndName(
            String studentLevel,
            String firstName,
            String middleName,
            String lastName
    );

    /**
     * Updates the disciplinary status of a student's enrollment for a specific
     * school year after validating the provided input.
     *
     * @param statusID the disciplinary status identifier.
     * @param studentID the unique identifier of the student.
     * @param schoolYear the school year of the student's enrollment.
     * @return return true if successfully query and false if not.
     */
    boolean setDisciplinaryStatusID(
            long statusID, String studentID, String schoolYear);
}
