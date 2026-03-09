package org.rocs.osd.data.dao.record.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.record.RecordDao;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for managing student records in the Office of Student Discipline System.
 * This class handles student record data from the database.
 */
public class RecordDaoImpl implements RecordDao
{

    /**
     * Finds student records by student ID, school year, and student level.
     * Returns an empty list if no records are found.
     * @return a list of Record objects matching the criteria.
     */
    @Override
    public List<Record> findAllBySchoolYear(String schoolYear)
    {
        List<Record> records = new ArrayList<>();
        String sql = """
                SELECT
                    r.recordID, r.dateOfViolation, r.dateOfResolution, r.remarks, r.status,

                    e.enrollmentID, e.studentID AS enrollStudentID, e.schoolYear, e.studentLevel, e.section,
                    e.departmentID AS enrollDeptID, e.disciplinaryStatusID AS enrollStatusID,

                    s.personID AS studentPersonID, s.address AS studentAddress, s.studentType,

                    ds.status AS disciplinaryStatus, ds.description AS statusDescription,

                    emp.employeeID, emp.personID AS empPersonID, emp.departmentID AS empDeptID, emp.employeeRole,

                    o.offenseID, o.offense, o.type AS offenseType, o.description AS offenseDescription,

                    da.actionID, da.action, da.description AS actionDescription

                    FROM record r
                    JOIN enrollment e ON r.enrollmentID = e.enrollmentID
                    JOIN student s ON e.studentID = s.studentID
                    JOIN disciplinaryStatus ds ON e.disciplinaryStatusID = ds.disciplinaryStatusID
                    JOIN employee emp ON r.employeeID = emp.employeeID
                    JOIN offense o ON r.offenseID = o.offenseID
                    JOIN disciplinaryAction da ON r.actionID = da.actionID
                    WHERE e.schoolYear = ?
                    ORDER BY r.dateOfViolation DESC;
                """;
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, schoolYear);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
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
                employee.setDepartmentId(rs.getString("empDeptID"));
                employee.setEmployeeRole(rs.getString("employeeRole"));

                Offense offense = new Offense();
                offense.setOffenseId(rs.getLong("offenseID"));
                offense.setOffense(rs.getString("offense"));
                offense.setType(rs.getString("offenseType"));
                offense.setDescription(rs.getString("offenseDescription"));

                DisciplinaryAction action = new DisciplinaryAction();
                action.setActionId(rs.getLong("actionID"));
                action.setActionName(rs.getString("action"));
                action.setDescription(rs.getString("actionDescription"));

                Record record = new Record();
                record.setRecordId(rs.getLong("recordID"));
                record.setEnrollment(enrollment);
                record.setEmployee(employee);
                record.setOffense(offense);
                record.setAction(action);
                record.setDateOfViolation(rs.getDate("dateOfViolation"));
                record.setDateOfResolution(rs.getDate("dateOfResolution"));
                record.setRemarks(rs.getString("remarks"));
                record.setStatus(RecordStatus.valueOf(rs.getString("status")));

                records.add(record);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception (findAllBySchoolYear): " + e.getMessage());
        }
        return records;
    }

    /**
     * Adds a new student record to the database.
     *
     * @param enrollmentID the enrollment ID of the student.
     * @param employeeID the ID of the employee recording the offense.
     * @param offenseID the ID of the offense.
     * @param dateOfViolation the date the violation occurred.
     * @param actionID the ID of the action taken.
     * @param remarks additional remarks about the record.
     * @param status the status of the record.
     * @return true if the record was added successfully, false otherwise.
     */
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
            stmt.setString(7, String.valueOf(status));
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
    public boolean updateRecord(Record record) {
        try (Connection con = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE record SET " +
                            "enrollmentID = ?," +
                            "employeeID = ?," +
                            "offenseID = ?, " +
                            "dateOfViolation = ?, " +
                            "dateOfResolution = ?, " +
                            "actionID = ?, " +
                            "remarks = ?, " +
                            "status = ? " +
                            "WHERE recordID = ?");
            stmt.setLong(1, record.getEnrollment().getEnrollmentId());
            stmt.setString(2, record.getEmployee().getEmployeeId());
            stmt.setLong(3, record.getOffense().getOffenseId());
            stmt.setDate(4, new java.sql.Date(record.getDateOfViolation().getTime()));
            stmt.setDate(5, new java.sql.Date(record.getDateOfResolution().getTime()));
            stmt.setLong(6, record.getAction().getActionId());
            stmt.setString(7, record.getRemarks());
            stmt.setString(8, String.valueOf(record.getStatus()));
            stmt.setLong(9, record.getRecordId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred." + e.getMessage());

            return false;
        }
    }

}
