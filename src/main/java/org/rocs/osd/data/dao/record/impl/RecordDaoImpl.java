package org.rocs.osd.data.dao.record.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryAction.DisciplinaryAction;
import org.rocs.osd.model.disciplinaryStatus.DisciplinaryStatus;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;
import org.rocs.osd.model.record.RecordStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * DAO implementation for managing student records in the Office of
 * Student Discipline System.
 *
 * Handles student record data from the database.
 */
public class RecordDaoImpl implements RecordDao {

    /**
     * Finds student records by school year.
     *
     * @param schoolYear the school year to filter.
     * @return list of matching Record objects.
     */
    @Override
    public List<Record> findAllBySchoolYear(String schoolYear) {
        List<Record> records = new ArrayList<>();

        String sql = """
            SELECT r.recordID, r.dateOfViolation, r.dateOfResolution,
                   r.remarks, r.status, e.enrollmentID,
                   e.studentID AS enrollStudentID, e.schoolYear,
                   e.studentLevel, e.section,
                   e.department AS enrollDept,
                   e.disciplinaryStatusID AS enrollStatusID,
                   s.personID AS studentPersonID,
                   s.address AS studentAddress, s.studentType,
                   ds.status AS disciplinaryStatus,
                   ds.description AS statusDescription,
                   emp.employeeID, emp.personID AS empPersonID,
                   emp.department AS empDept, emp.employeeRole,
                   o.offenseID, o.offense, o.type AS offenseType,
                   o.description AS offenseDescription,
                   da.actionID, da.action,
                   da.description AS actionDescription
            FROM record r
            JOIN enrollment e ON r.enrollmentID = e.enrollmentID
            JOIN student s ON e.studentID = s.studentID
            JOIN disciplinaryStatus ds
                 ON e.disciplinaryStatusID = ds.disciplinaryStatusID
            JOIN employee emp ON r.employeeID = emp.employeeID
            JOIN offense o ON r.offenseID = o.offenseID
            JOIN disciplinaryAction da ON r.actionID = da.actionID
            WHERE e.schoolYear = ?
            ORDER BY r.dateOfViolation DESC
            """;

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, schoolYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setPersonID(rs.getLong("studentPersonID"));
                student.setAddress(rs.getString("studentAddress"));
                student.setStudentType(rs.getString("studentType"));

                DisciplinaryStatus status = new DisciplinaryStatus();
                status.setStatus(rs.getString("disciplinaryStatus"));
                status.setDescription(rs.getString("statusDescription"));

                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getLong("enrollmentID"));
                enrollment.setStudent(student);
                enrollment.setSchoolYear(rs.getString("schoolYear"));
                enrollment.setStudentLevel(rs.getString("studentLevel"));
                enrollment.setSection(rs.getString("section"));
                enrollment.setDisciplinaryStatus(status);

                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("employeeID"));
                employee.setPersonID(rs.getLong("empPersonID"));
                employee.setDepartment(
                        Department.valueOf(rs.getString("empDept"))
                );
                employee.setEmployeeRole(rs.getString("employeeRole"));

                Offense offense = new Offense();
                offense.setOffenseId(rs.getLong("offenseID"));
                offense.setOffense(rs.getString("offense"));
                offense.setType(rs.getString("offenseType"));
                offense.setDescription(
                        rs.getString("offenseDescription")
                );

                DisciplinaryAction action = new DisciplinaryAction();
                action.setActionId(rs.getLong("actionID"));
                action.setActionName(rs.getString("action"));
                action.setDescription(
                        rs.getString("actionDescription")
                );

                Record record = new Record();
                record.setRecordId(rs.getLong("recordID"));
                record.setEnrollment(enrollment);
                record.setEmployee(employee);
                record.setOffense(offense);
                record.setAction(action);
                record.setDateOfViolation(
                        rs.getDate("dateOfViolation")
                );
                record.setDateOfResolution(
                        rs.getDate("dateOfResolution")
                );
                record.setRemarks(rs.getString("remarks"));
                record.setStatus(
                        RecordStatus.valueOf(rs.getString("status"))
                );

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println(
                    "SQL Exception (findAllBySchoolYear): "
                            + e.getMessage()
            );
        }
        return records;
    }

    /**
     * Adds a new student record into the database.
     */
    @Override
    public boolean addStudentRecord(long enrollmentID, String employeeID,
                                    long offenseID, Date dateOfViolation,
                                    long actionID, String remarks,
                                    RecordStatus status) {

        try (Connection con = ConnectionHelper.getConnection()) {

            String sql =
                    "INSERT INTO record (enrollmentID, employeeID, " +
                            "offenseID, dateOfViolation, actionID, remarks, " +
                            "status) VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setLong(1, enrollmentID);
            stmt.setString(2, employeeID);
            stmt.setLong(3, offenseID);
            stmt.setDate(4, (java.sql.Date) dateOfViolation);
            stmt.setLong(5, actionID);
            stmt.setString(6, remarks);
            stmt.setString(7, String.valueOf(status));

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(
                    "SQL Exception (addStudentRecord): "
                            + e.getMessage()
            );
            return false;
        }
    }

    /**
     * Updates an existing student record.
     */
    @Override
    public boolean updateRecord(Record record) {

        try (Connection con = ConnectionHelper.getConnection()) {

            String sql =
                    "UPDATE record SET enrollmentID = ?, employeeID = ?, " +
                            "offenseID = ?, " +
                            "dateOfViolation = ?," +
                            " dateOfResolution = ?, " +
                            "actionID = ?, " +
                            "remarks = ?, " +
                            "status = ? " +
                            "WHERE recordID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setLong(1, record.getEnrollment().getEnrollmentId());
            stmt.setString(2, record.getEmployee().getEmployeeId());
            stmt.setLong(3, record.getOffense().getOffenseId());
            stmt.setDate(4,
                    new java.sql.Date(
                            record.getDateOfViolation().getTime()
                    )
            );

            if (record.getDateOfResolution() != null) {
                stmt.setDate(5,
                        new java.sql.Date(
                                record.getDateOfResolution().getTime()
                        )
                );
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setLong(6, record.getAction().getActionId());
            stmt.setString(7, record.getRemarks());
            stmt.setString(8, String.valueOf(record.getStatus()));
            stmt.setLong(9, record.getRecordId());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(
                    "SQL Exception (updateRecord): "
                            + e.getMessage()
            );
            return false;
        }
    }
}