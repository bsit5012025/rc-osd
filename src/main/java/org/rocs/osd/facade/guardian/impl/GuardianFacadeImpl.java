package org.rocs.osd.facade.guardian.impl;

import org.rocs.osd.data.dao.guardian.GuardianDao;
import org.rocs.osd.data.dao.guardian.impl.GuardianDaoImpl;
import org.rocs.osd.facade.guardian.GuardianFacade;
import org.rocs.osd.model.person.studentGuardian.StudentGuardian;

import java.util.ArrayList;
import java.util.List;

public class GuardianFacadeImpl implements GuardianFacade {

    private final GuardianDao guardianDao;

    public GuardianFacadeImpl() {
        this.guardianDao = new GuardianDaoImpl();
    }

    @Override
    public List<StudentGuardian> getGuardianByStudentId(String studentID) {
        if (studentID == null || studentID.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return guardianDao.findGuardianByStudentId(studentID);
    }
}
