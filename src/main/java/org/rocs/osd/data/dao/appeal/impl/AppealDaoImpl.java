package org.rocs.osd.data.dao.appeal.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of AppealDao that handles database operations for Appeal records in the Office of Student Discipline System.
 * Handles database operations such as saving, retrieving, and updating appeals.
 */
public class AppealDaoImpl implements AppealDao {

    /**
     * Saves a new appeal record to the database.
     * @param appeal the Appeal object to save.
     */
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

                Student student = new Student();
                student.setStudentId(rs.getString("studentID"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));

                enrollment.setStudent(student);
                appeal.setEnrollment(enrollment);

                list.add(appeal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Updates the status of an existing appeal record.
     * @param appealId the ID of the appeal to update.
     * @param status the new status value.
     */
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

