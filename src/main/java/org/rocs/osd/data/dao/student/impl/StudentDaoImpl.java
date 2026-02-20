package org.rocs.osd.data.dao.student.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.student.StudentDao;
import org.rocs.osd.model.person.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao
{

    @Override
    public List<Student> findAllStudents() {
        List<Student> students = new ArrayList<>();

        String sql = """
        SELECT
            s.studentID,
            s.address,
            s.studentType,
            s.departmentID,
            p.personID,
            p.firstName,
            p.middleName,
            p.lastName
        FROM student s
        JOIN person p ON s.personID = p.personID
        ORDER BY p.lastName
        """;

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Student student = new Student();

                student.setPersonID(rs.getLong("personID"));
                student.setFirstName(rs.getString("firstName"));
                student.setMiddleName(rs.getString("middleName"));
                student.setLastName(rs.getString("lastName"));
                student.setStudentId(rs.getString("studentID"));
                student.setAddress(rs.getString("address"));
                student.setStudentType(rs.getString("studentType"));
                student.setDepartmentId(rs.getLong("departmentID"));

                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR (findAllStudents): " + e.getMessage());
        }

        return students;
    }

    @Override
    public Student findStudentByID(String studentID) {
        Student student = null;

        String sql = """
        SELECT
            s.studentID,
            s.address,
            s.studentType,
            s.departmentID,
            p.personID,
            p.firstName,
            p.middleName,
            p.lastName
        FROM student s
        JOIN person p ON s.personID = p.personID
        WHERE s.studentID = ?
        """;

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, studentID);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    student = new Student();

                    student.setPersonID(rs.getLong("personID"));
                    student.setFirstName(rs.getString("firstName"));
                    student.setMiddleName(rs.getString("middleName"));
                    student.setLastName(rs.getString("lastName"));
                    student.setStudentId(rs.getString("studentID"));
                    student.setAddress(rs.getString("address"));
                    student.setStudentType(rs.getString("studentType"));
                    student.setDepartmentId(rs.getLong("departmentID"));
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL ERROR (findStudentByID): " + e.getMessage());
        }

        return student;
    }
}
