package org.rocs.osd.facade.enrollment.impl;

import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.model.enrollment.Enrollment;

import java.util.List;

/**
 * Facade implementation for Enrollment operations in the Office of Student
 * Discipline System.
 */
public class EnrollmentFacadeImpl implements EnrollmentFacade {

    /**
     * DAO for handling enrollment data operations.
     */
    private final EnrollmentDao enrollmentDao;

    /**
     * Constructor for injecting an EnrollmentDao implementation.
     * @param pEnrollmentDao the DAO to use for database operations.
     */
    public EnrollmentFacadeImpl(EnrollmentDao pEnrollmentDao) {
        this.enrollmentDao = pEnrollmentDao;
    }

    /**
     * Retrieves a list of the latest enrollment records for all students.
     *
     * @return a List of Enrollment objects representing the most recent
     * enrollment of each student.
     */
    @Override
    public List<Enrollment> getAllLatestEnrollments() {
        return enrollmentDao.findAllLatestEnrollments();
    }

    /**
     * Retrieves all enrollment records associated with a specific student.
     *
     * @param studentId the unique ID of the student.
     * @return a List of Enrollment objects for the given student.
     */
    @Override
    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        return enrollmentDao.findEnrollmentsByStudentId(studentId);
    }

    /**
     * Retrieves the most recent enrollment record for a specific student.
     * @param studentId the unique ID of the student.
     * @return the latest Enrollment object for the student,
     * or null if none exist.
     */
    @Override
    public Enrollment getLatestEnrollmentByStudentId(String studentId) {
        List<Enrollment> enrollments = enrollmentDao
        .findEnrollmentsByStudentId(studentId);

        if (enrollments == null || enrollments.isEmpty()) {
            return null;
        }
        return enrollments.get(0);
    }

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
    @Override
    public Enrollment getEnrollmentsByStudentLevelAndName(
            String studentLevel,
            String firstName,
            String middleName,
            String lastName
    ) {
        if (studentLevel == null || studentLevel.isEmpty()
                || firstName == null || firstName.isEmpty()
                || middleName == null || middleName.isEmpty()
                || lastName == null || lastName.isEmpty()
        ) {
            return null;
        }

        return enrollmentDao.findEnrollmentsByStudentLevelAndName(
                studentLevel,
                firstName,
                middleName,
                lastName
        );
    }

    /**
     * Updates the disciplinary status of a student's enrollment for a specific
     * school year after validating the provided input.
     *
     * @param statusID the disciplinary status identifier.
     * @param studentID the unique identifier of the student.
     * @param schoolYear the school year of the student's enrollment.
     * @return return true if successfully query and false if not.
     */
    @Override
    public boolean setDisciplinaryStatusID(
            long statusID, String studentID, String schoolYear)  {
        if (statusID < 1 || statusID > 5) {
            return false;
        }
        if (studentID == null || studentID.isBlank()) {
            return false;
        }
        if (schoolYear == null || schoolYear.isBlank()) {
            return false;
        }

        return enrollmentDao.setDisciplinaryStatusID(
                statusID, studentID, schoolYear);
    }
}
