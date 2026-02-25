package org.rocs.osd.data.dao.student.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.student.StudendDao;
import org.rocs.osd.model.person.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the StudendDao interface.
 * This class handles student data from the database, including records.
 */
public class StudentDaoImpl implements StudendDao
{

    /**
     * Finds a student and their record by student ID.
     * Returns an empty Student object if no match is found.
     * @param StudentId the ID of the student to search.
     * @return a Student object with student and record info.
     */
    @Override
    public Student findStudentWithRecordById(String StudentId)
    {
        Student student = new Student();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                        "SELECT " +
                            "s.studentID, " +
                            "s.personID, " +
                            "s.address, " +
                            "s.studentType, " +
                            "s.departmentID, " +
                            "p.lastName, " +
                            "p.firstName, " +
                            "p.middleName " +
                            "FROM student s " +
                            "JOIN person p ON s.personID = p.personID " +
                            "JOIN enrollment e ON s.studentID = e.studentID " +
                            "JOIN record r ON e.enrollmentID = r.enrollmentID " +
                            "WHERE s.studentID = ?");

            statement.setString(1, StudentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));
                student.setStudentType(rs.getString("studentType"));
                student.setDepartmentId(rs.getString("departmentID"));
                student.setLastName(rs.getString("lastName"));
                student.setFirstName(rs.getString("firstName"));
                student.setMiddleName(rs.getString("middleName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    /**
     * Finds a student and their record by their full name.
     * Returns an empty Student object if no match is found.
     * @param lastName the student's last name.
     * @param firstName the student's first name.
     * @param middleName the student's middle name.
     * @return a Student object with student and record info.
     */
    @Override
    public Student findStudentWithRecordByName(String lastName, String firstName, String middleName)
    {
        Student student = new Student();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                            "SELECT " +
                                "s.studentID, " +
                                "s.personID, " +
                                "s.address, " +
                                "s.studentType, " +
                                "s.departmentID, " +
                                "p.lastName, " +
                                "p.firstName, " +
                                "p.middleName " +
                                "FROM student s " +
                                "JOIN person p ON s.personID = p.personID " +
                                "JOIN enrollment e ON s.studentID = e.studentID " +
                                "JOIN record r ON e.enrollmentID = r.enrollmentID " +
                                "WHERE LOWER(p.lastName) = LOWER(?) " +
                                "AND LOWER(p.firstName) = LOWER(?) " +
                                "AND LOWER(p.middleName) = LOWER(?)");

            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, middleName);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));
                student.setStudentType(rs.getString("studentType"));
                student.setDepartmentId(rs.getString("departmentID"));
                student.setLastName(rs.getString("lastName"));
                student.setFirstName(rs.getString("firstName"));
                student.setMiddleName(rs.getString("middleName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }
}
