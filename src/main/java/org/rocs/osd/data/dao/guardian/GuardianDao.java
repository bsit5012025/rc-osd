package org.rocs.osd.data.dao.guardian;

import org.rocs.osd.model.person.studentGuardian.StudentGuardian;
import java.util.List;

/**
 * DAO interface for managing guardian information in the Office of
 * Student Discipline System.
 * Provides methods to retrieve guardians linked to students.
 */
public interface GuardianDao {

    /**
     * Retrieves a list of guardians associated with a given student ID.
     * @param studentID the ID of the student.
     * @return a list of StudentGuardian objects linking the student to
     * their guardians.
     */
    List<StudentGuardian> findGuardianByStudentId(String studentID);
}
