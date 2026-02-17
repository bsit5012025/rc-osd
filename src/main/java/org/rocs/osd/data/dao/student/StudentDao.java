package org.rocs.osd.data.dao.student;

import org.rocs.osd.model.person.student.Student;

import java.util.List;

public interface StudentDao
{
    List<Student> findAllStudents();
    Student findStudentByID(String studentID);
}
