package org.rocs.osd.facade.guardian.impl;

import org.rocs.osd.data.dao.guardian.GuardianDao;
import org.rocs.osd.data.dao.guardian.impl.GuardianDaoImpl;
import org.rocs.osd.facade.guardian.GuardianFacade;
import org.rocs.osd.model.person.student.guardian.StudentGuardian;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade implementation for managing Guardian records in the Office of
 * Student Discipline System.
 */
public class GuardianFacadeImpl implements GuardianFacade {

    /**
     * DAO object used for retrieving guardian data from the database.
     * */
    private final GuardianDao guardianDao;
    /**
     * Default constructor.
     * */
    public GuardianFacadeImpl() {
        this.guardianDao = new GuardianDaoImpl();
    }

    /**
     * Retrieves all guardians associated with a specific student ID.
     * @param pStudentID the unique ID of the student.
     * @return a List of StudentGuardian objects associated with the student.
     */
    @Override
    public List<StudentGuardian> getGuardianByStudentId(String pStudentID) {
        if (pStudentID == null || pStudentID.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return guardianDao.findGuardianByStudentId(pStudentID);
    }
}
