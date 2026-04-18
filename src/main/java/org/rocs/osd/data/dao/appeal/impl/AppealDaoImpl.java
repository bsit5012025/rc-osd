package org.rocs.osd.data.dao.appeal.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of AppealDao that handles database operations for Appeal
 * records in the Office of Student Discipline System.
 * Provides methods to retrieve pending appeals and update appeal statuses.
 */
public class AppealDaoImpl implements AppealDao {
    /**
     * Logger for logging errors and debug.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AppealDaoImpl.class);

    /**
     * Retrieves all appeals by its status along with related
     * student, enrollment, and record details.
     *
     * @param status the appeal status (PENDING, APPROVED, DENIED)
     * @return list of appeals
     */
    @Override
    public List<Appeal> findAppealsByStatus(String status) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Fetching appeals with status: {}", status);
        }
        List<Appeal> list = new ArrayList<>();

        String sql = """
               SELECT a.appealID,
                      a.recordID,
                      a.enrollmentID,
                      a.message,
                      a.dateFiled,
                      a.status,
                      a.dateProcessed,
                      a.remarks,
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
               WHERE a.status = ?
               ORDER BY a.dateFiled DESC
               """;

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Appeal appeal = new Appeal();
                    appeal.setAppealID(rs.getLong("appealID"));
                    appeal.setMessage(rs.getString("message"));
                    appeal.setDateFiled(rs.getDate("dateFiled"));
                    appeal.setStatus(rs.getString("status"));
                    appeal.setRemarks(rs.getString("remarks"));
                    appeal.setDateProcessed(rs.getDate("dateProcessed"));

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
            }
        } catch (SQLException e) {
            LOGGER.error("Error while fetching appeals for status '{}': {}",
                    status, e);
            throw new RuntimeException("Error fetching appeal "
                    + "by status", e);
        }

        return list;
    }

    /**
     * Updates the status of an appeal in the database.
     * Also saves remarks and sets the processed date.
     *
     * @param appealId ID of the appeal to update
     * @param status   new status (APPROVED / DENIED)
     * @param remarks  optional remarks for the appeal
     */
    @Override
    public void processAppeal(long appealId, String status, String remarks) {
        LOGGER.info("Processing appeal ID: {} with new status: {}",
                appealId, status);
        String sql = """
                   UPDATE appeal
                   SET status = ?,
                   remarks = ?,
                   dateProcessed = CURRENT_DATE
                   WHERE appealID = ?
                   """;

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, remarks);
            ps.setLong(3, appealId);

            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Failed to process appeal ID {}: {}",
                    appealId, e);
            throw new RuntimeException("Error processing appeal", e);
        }
    }
}
