package org.rocs.osd.facade.enrollment.impl;

import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.facade.enrollment.EnrollmentFacade;
import org.rocs.osd.model.enrollment.Enrollment;

import java.util.List;

public class EnrollmentFacadeImpl implements EnrollmentFacade {

    private final EnrollmentDao enrollmentDao;

    public EnrollmentFacadeImpl(EnrollmentDao enrollmentDao) {this.enrollmentDao = enrollmentDao;}

    public List<Enrollment> getAllLatestEnrollments() {
        return enrollmentDao.findAllLatestEnrollments();
    }

    public List<Enrollment> getEnrollmentsByStudentId(String studentId) {
        return enrollmentDao.findEnrollmentsByStudentId(studentId);
    }

    public Enrollment getLatestEnrollmentByStudentId(String studentId) {
        List<Enrollment> enrollments = enrollmentDao.findEnrollmentsByStudentId(studentId);

        if (enrollments == null || enrollments.isEmpty()) {
            return null;
        }
        return enrollments.get(0);
    }

}
