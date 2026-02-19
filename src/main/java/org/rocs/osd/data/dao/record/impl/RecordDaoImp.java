package org.rocs.osd.data.dao.record.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.model.record.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImp implements RecordDao
{

    // This class will handle the student record data from the database
    @Override
    public List<Record> findStudentByIdAndEnrolment(String studentID, String schoolYear, String studentLevel)
    {
        List<Record> studentRecord = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                        "SELECT " +
                                "    r.recordID, " +
                                "    r.enrollmentID, " +
                                "    r.employeeID, " +
                                "    r.offenseID, " +
                                "    r.dateOfViolation, " +
                                "    r.actionID, " +
                                "    r.dateOfResolution, " +
                                "    r.remarks, " +
                                "    r.status " +
                                "FROM record r " +
                                "JOIN enrollment e ON r.enrollmentID = e.enrollmentID " +
                                "JOIN student s ON e.studentID = s.studentID " +
                                "WHERE s.studentID = ? " +
                                "  AND (? IS NULL OR e.schoolYear = ? ) " +
                                "  AND (? IS NULL OR e.studentLevel = ? )");

            statement.setString(1, studentID);
            statement.setString(2, schoolYear);
            statement.setString(3, schoolYear);
            statement.setString(4, studentLevel);
            statement.setString(5, studentLevel);
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
    public boolean addStudentRecord(long enrollmentID, String employeeID,
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

    @Override
    public boolean updateStudentRecordStatusById(long recordID, String status) {
        String sql = "UPDATE RECORD SET status = ? WHERE recordID = ?";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setLong(2, recordID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SQL Exception (updateStudentRecordStatusById): " + e.getMessage());
        }

        return false;
    }
}
