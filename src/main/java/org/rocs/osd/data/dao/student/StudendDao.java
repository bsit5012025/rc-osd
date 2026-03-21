package org.rocs.osd.data.dao.student;

import org.rocs.osd.model.person.student.Student;

/**
 * DAO interface for Student.
 * This interface defines the methods for retrieving Student information from
 * the database.
 */
public interface StudendDao {

    /**
     * Finds a student and their associated record by student ID.
     * @param studentID the ID of the student to search.
     * @return a Student object with student and record information.
     */
    Student findStudentWithRecordById(String studentID);

    /**
     * Finds a student and their associated record by full name.
     * @param lastName the student's last name.
     * @param firstName  the student's first name.
     * @param middleName the student's middle name.
     * @return a Student object with student and record information.
     */
    Student findStudentWithRecordByName(String lastName, String firstName,
                                        String middleName);
}