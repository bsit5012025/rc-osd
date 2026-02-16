package org.rocs.osd.data.dao.student;

import org.rocs.osd.model.person.student.Student;

public interface StudendDao
{
    Student findStudentWithRecordById(String studentID);
    Student findStudentWithRecordByName(String lastName, String firstName, String middleName);
}
