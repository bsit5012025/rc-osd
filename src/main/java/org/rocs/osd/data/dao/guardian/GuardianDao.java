package org.rocs.osd.data.dao.guardian;

import org.rocs.osd.model.person.studentGuardian.StudentGuardian;

import java.util.List;

public interface GuardianDao {
    List<StudentGuardian> findGuardianByStudentId(String studentID);
}
