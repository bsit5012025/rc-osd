package org.rocs.osd.facade.guardian;

import org.rocs.osd.model.person.studentguardian.StudentGuardian;

import java.util.List;

/**
 * Facade interface for managing Guardian records in the Office of Student
 * Discipline System.
 */
public interface GuardianFacade {

    /**
     * Retrieves all guardians associated with a given student ID.
     *
     * @param studentID the unique ID of the student.
     * @return a List of StudentGuardian objects linked to the student.
     */
    List<StudentGuardian> getGuardianByStudentId(String studentID);
}
