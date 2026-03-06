package org.rocs.osd.data.dao.enrollment;

import org.rocs.osd.model.enrollment.Enrollment;

import java.util.List;

public interface EnrollmentDao {
    long findEnrollmentIdByStudentId(String studentId);
    List<Enrollment> findEnrollmentsByStudentId(String studentId);
}
