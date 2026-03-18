package org.rocs.osd.facade.enrollment;

import org.rocs.osd.model.enrollment.Enrollment;

import java.util.List;

public interface EnrollmentFacade {

    List<Enrollment> getAllLatestEnrollments();
    List<Enrollment> getEnrollmentsByStudentId(String studentId);
    Enrollment getLatestEnrollmentByStudentId(String studentId);

}
