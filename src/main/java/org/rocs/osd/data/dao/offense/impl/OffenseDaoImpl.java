package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.login.Student;

import java.sql.*;
import java.util.ArrayList;

public class OffenseDaoImpl implements OffenseDao
{

    @Override
    public ArrayList<Student> getStudentById(String StudentId)
    {
        ArrayList<Student> studentOffenceList = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT " +
                            "e.studentID, " +
                            "p.lastName || ', ' || p.firstName AS studentName, " +
                            "o.type AS offense_type, " +
                            "r.dateOfViolation " +
                            "FROM person p " +
                            "JOIN student s ON p.personID = s.personID " +
                            "JOIN enrollment e ON s.studentID = e.studentID " +
                            "JOIN record r ON r.enrollmentID = e.enrollmentID " +
                            "JOIN offense o ON r.offenseID = o.offenseID " +
                            "WHERE e.studentID = ? " +
                            "ORDER BY r.dateOfViolation DESC");
            statement.setString(1, StudentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentID(String.valueOf(rs.getLong("studentID")));
                student.setStudentName(rs.getString("studentName"));
                student.setOffenseType(rs.getString("offense_type"));
                student.setDateOfViolation(rs.getDate("dateOfViolation"));
                studentOffenceList.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentOffenceList;
    }

    @Override
    public void addStudentViolation(String recordID, String enrollmentID, String prefectID,
                                    String offenseID, String dateOfViolation, String actionID,
                                    String remarks, String status)
    {

        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    " INSERT INTO record (" +
                    "    recordID," +
                    "    enrollmentID," +
                    "    prefectID," +
                    "    offenseID," +
                    "    dateOfViolation," +
                    "    actionID," +
                    "    remarks," +
                    "    status" +
                    "    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

            stmt.setString(1, recordID);
            stmt.setString(2, enrollmentID);
            stmt.setString(3, prefectID);
            stmt.setString(4, offenseID);
            stmt.setString(5, dateOfViolation);
            stmt.setString(6, actionID);
            stmt.setString(7, remarks);
            stmt.setString(8, status);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            System.out.println("An SQL Exception occurred." + e.getMessage());
        }
    }
}
