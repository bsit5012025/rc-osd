package org.rocs.osd.data.dao.appeal.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.record.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppealDaoImpl implements AppealDao {

    @Override
    public List<Appeal> findPendingAppealsWithDetails() {

        List<Appeal> list = new ArrayList<>();
        String sql = """
            SELECT a.appealID,
                   a.recordID,
                   a.enrollmentID,
                   a.message,
                   a.dateFiled,
                   a.status,
                   s.studentID,
                   p.firstName,
                   p.lastName,
                   o.offense
                    FROM appeal a
                    JOIN record r ON a.recordID = r.recordID
                    JOIN offense o ON r.offenseID = o.offenseID
                    JOIN enrollment e ON a.enrollmentID = e.enrollmentID
                    JOIN student s ON e.studentID = s.studentID
                    JOIN person p ON s.personID = p.personID
                    WHERE a.status = 'PENDING'
                    ORDER BY a.dateFiled DESC
        """;

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appeal appeal = new Appeal();
                appeal.setAppealID(rs.getLong("appealID"));
                appeal.setMessage(rs.getString("message"));
                appeal.setDateFiled(rs.getDate("dateFiled"));
                appeal.setStatus(rs.getString("status"));

                Record record = new Record();
                record.setRecordId(rs.getLong("recordID"));
                record.setRemarks(rs.getString("offense"));
                appeal.setRecord(record);

                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getLong("enrollmentID"));
                enrollment.setStudentId(rs.getString("studentID"));
                String fullName = rs.getString("firstName") + " " + rs.getString("lastName");
                appeal.setStudentFullName(fullName);
                appeal.setEnrollment(enrollment);

                list.add(appeal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    @Override
    public void updateAppealStatus(long appealId, String status) {

        String sql = "UPDATE appeal SET status = ? WHERE appealID = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, appealId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
