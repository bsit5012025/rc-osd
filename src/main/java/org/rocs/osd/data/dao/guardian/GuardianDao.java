package org.rocs.osd.data.dao.guardian;

import org.rocs.osd.model.person.guardian.Guardian;

import java.util.List;

public interface GuardianDao {
    public List<Guardian> findGuardianByStudentId(String studentID);
}
