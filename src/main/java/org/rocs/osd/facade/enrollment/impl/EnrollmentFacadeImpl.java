package org.rocs.osd.facade.enrollment.impl;

import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.model.enrollment.Enrollment;

import java.util.List;

/**
 * Facade implementation for managing Enrollment records in the Office of Student Discipline System.
 * Handles methods to retrieve enrollment data.
 */
public class EnrollmentFacadeImpl implements EnrollmentFacade {

    private final EnrollmentDao enrollmentDao;


    /**
     * Constructs an EnrollmentFacadeImpl with the specified EnrollmentDao.
     *
     * @param enrollmentDao the DAO used for enrollment data access.
     */
    public EnrollmentFacadeImpl(EnrollmentDao enrollmentDao) {this.enrollmentDao = enrollmentDao;}

    /**
     * Retrieves all latest enrollment records.
     *
     * @return a List of latest Enrollment objects.
     */
    public List<Enrollment> getAllLatestEnrollments() {
        return enrollmentDao.findAllLatestEnrollments();
    }

    /**
     * Retrieves all enrollments of a student.
     *
     * @param studentId the unique ID of the student.
     *
     * @return a List of Enrollment objects.
     */
    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        return enrollmentDao.findEnrollmentsByStudentId(studentId);
    }

    /**
     * Retrieves the latest enrollment of a student.
     *
     * @param studentId the unique ID of the student.
     *
     * @return the latest Enrollment object, or null if not found.
     */
    public Enrollment getLatestEnrollmentByStudentId(String studentId) {
        List<Enrollment> enrollments = enrollmentDao.findEnrollmentsByStudentId(studentId);

        if (enrollments == null || enrollments.isEmpty()) {
            return null;
        }
        return enrollments.get(0);
    }

}
