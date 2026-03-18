package org.rocs.osd.facade.guardian;

import org.rocs.osd.model.person.studentGuardian.StudentGuardian;

import java.util.List;

public interface GuardianFacade {
    List<StudentGuardian> getGuardianByStudentId(String studentID);
}