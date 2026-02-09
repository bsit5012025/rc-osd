package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.login.Student;

import java.sql.*;

public class OffenseDaoImpl implements OffenseDao
{

    @Override
    public Student getStudentById(String StudentId)
    {
        Student student = new Student();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "    SELECT" +
                    "    e.studentID," +
                    "    p.lastName || ', ' || p.firstName AS studentName," +
                    "    o.type AS offense_type," +
                    "    r.dateOfViolation" +
                    "    FROM person p" +
                    "    JOIN login l ON p.personID = l.personID" +
                    "    JOIN student s ON l.id = s.userID" +
                    "    JOIN enrollment e ON s.studentID = e.studentID" +
                    "    JOIN record r ON r.enrollmentID = e.enrollmentID" +
                    "    JOIN offense o ON r.offenseID = o.offenseID" +
                    "    WHERE e.studentID = ?" +
                    "    ORDER BY r.dateOfViolation DESC;");
            statement.setString(1, StudentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                student.setStudentID(String.valueOf(rs.getLong("studentID")));
                student.setStudentName(rs.getString("studentName"));
                student.setOffenseType(rs.getString("offense_type"));
                student.setDateOfViolation(rs.getDate("dateOfViolation"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
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
