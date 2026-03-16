package org.rocs.osd.data.dao.guardianNumber;

import org.rocs.osd.model.person.guardian.Guardian;

import java.util.List;

public interface GuardianNumberDao {
    List<Guardian> getGuardiansByStudentID(String studentID);
}
