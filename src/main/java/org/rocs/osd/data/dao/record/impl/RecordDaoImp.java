package org.rocs.osd.data.dao.record.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImp implements RecordDao
{
    @Override
    public List<Record> findStudentByIdAndEnrolment(String studentID, String schoolYear, String studentLevel)
    {
        List<Record> studentRecord = new ArrayList<>();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = conn.prepareStatement(
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

            stmt.setString(1, studentID);
            stmt.setString(2, schoolYear);
            stmt.setString(3, schoolYear);
            stmt.setString(4, studentLevel);
            stmt.setString(5, studentLevel);
            ResultSet rs = stmt.executeQuery();

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
                record.setStatus(RecordStatus.valueOf(rs.getString("status")));
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
                                    String remarks, RecordStatus status)
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
            stmt.setDate(4, dateOfViolation);
            stmt.setLong(5, actionID);
            stmt.setString(6, remarks);
            stmt.setString(7, status.toString());
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
