package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.record.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OffenseDaoImpl implements OffenseDao
{

    @Override
    public List<Record> getStudentRecord(String studentID)
    {
        List<Record> studentRecord = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                            "SELECT" +
                                "r.recordID," +
                                "r.enrollmentID," +
                                "r.employeeID," +
                                "r.offenseID," +
                                "r.dateOfViolation," +
                                "r.actionID," +
                                "r.dateOfResolution," +
                                "r.remarks," +
                                "r.status" +
                                "FROM record r" +
                                "JOIN enrollment e ON r.enrollmentID = e.enrollmentID " +
                                "JOIN student s ON e.studentID = s.studentID " +
                                "WHERE s.studentID = ?");

            statement.setString(1, studentID);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                Record record = new Record();
                record.setRecordId(rs.getLong("recordID"));
                record.setEnrollmentId(rs.getLong("enrollmentID"));
                record.setEmployeeId(rs.getString("employeeID"));
                record.setOffenseId(rs.getLong("offenseID"));
                record.setDateOfViolation(rs.getDate("dateOfViolation"));
                record.setActionId(rs.getLong("actionID"));
                record.setDateOfResolution(rs.getDate("dateOfResolution"));
                record.setRemarks(rs.getString("remarks"));
                record.setStatus(rs.getString("status"));
                studentRecord.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentRecord;
    }

    @Override
    public Offense getStudentOffense(String offenseID)
    {
        Offense offense = new Offense();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT " +
                            "o.offenseID," +
                            "o.offense," +
                            "o.type," +
                            "o.description" +
                            "FROM offense o where offenseID = ?");

            statement.setString(1, offenseID);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                offense.setOffenseId(rs.getLong("offenseID"));
                offense.setOffense(rs.getString("offense"));
                offense.setType(rs.getString("type"));
                offense.setDescription(rs.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offense;
    }

    @Override
    public boolean addStudentViolation(long enrollmentID, String employeeID,
                                       long offenseID, Date dateOfViolation, long  actionID,
                                       String remarks, String status)
    {

        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                        " INSERT INTO record (" +
                            "    enrollmentID," +
                            "    employeeID," +
                            "    offenseID," +
                            "    dateOfViolation," +
                            "    actionID," +
                            "    remarks," +
                            "    status" +
                            "    ) VALUES (?, ?, ?, ?, ?, ?, ?)");

            stmt.setLong(1, enrollmentID);
            stmt.setString(2, employeeID);
            stmt.setLong(3, offenseID);
            stmt.setDate(4, new Date(dateOfViolation.getTime()));
            stmt.setLong(5, actionID);
            stmt.setString(6, remarks);
            stmt.setString(7, status);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e)
        {
            System.out.println("An SQL Exception occurred." + e.getMessage());

            return false;
        }
    }
}
