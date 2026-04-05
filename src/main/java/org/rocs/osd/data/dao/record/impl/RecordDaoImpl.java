package org.rocs.osd.data.dao.record.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.record.RecordDao;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinary.action.DisciplinaryAction;
import org.rocs.osd.model.disciplinary.status.DisciplinaryStatus;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
                   da.description AS actionDescription,
                   p.firstName, p.lastName,
                   s.studentID AS studId
            FROM record r
            JOIN enrollment e ON r.enrollmentID = e.enrollmentID
            JOIN student s ON e.studentID = s.studentID
            JOIN disciplinaryStatus ds
                 ON e.disciplinaryStatusID = ds.disciplinaryStatusID
            JOIN employee emp ON r.employeeID = emp.employeeID
            JOIN offense o ON r.offenseID = o.offenseID
            JOIN disciplinaryAction da ON r.actionID = da.actionID
            JOIN person p ON s.personID = p.personID
            WHERE e.schoolYear = ?
            ORDER BY r.dateOfViolation DESC
            """;

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, schoolYear);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Student student = new Student();
                    student.setPersonID(rs.getLong("studentPersonID"));
                    student.setAddress(rs.getString("studentAddress"));
                    student.setStudentType(rs.getString("studentType"));
                    student.setStudentId(rs.getString("studId"));
                    student.setFirstName(rs.getString("firstName"));
                    student.setLastName(rs.getString("lastName"));

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
        String sql =
                "INSERT INTO record (enrollmentID, employeeID, "
                        + "offenseID, dateOfViolation, actionID, remarks, "
                        + "status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

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
        String sql =
                "UPDATE record SET enrollmentID = ?, employeeID = ?, "
                        + "offenseID = ?, "
                        + "dateOfViolation = ?,"
                        + " dateOfResolution = ?, "
                        + "actionID = ?, "
                        + "remarks = ?, "
                        + "status = ? "
                        + "WHERE recordID = ?";
        try (Connection con = ConnectionHelper.getConnection();

             PreparedStatement stmt = con.prepareStatement(sql)) {

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

    @Override
    public List<Record> findRecordListByDepartment(
            Department department, String schoolYear) {
        List<Record> records = new ArrayList<>();
        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "SELECT "
                             + "r.recordID, r.dateOfViolation, "
                             + "r.dateOfResolution, "
                             + "r.remarks, r.status, "
                             + "e.enrollmentID, e.studentID, e.schoolYear, "
                             + "e.studentLevel, e.section, e.department, "
                             + "sp.firstName, sp.middleName, sp.lastName, "
                             + "o.offense, o.type, "
                             + "emp.employeeID, ep.firstName AS empFirstName, "
                             + "ep.lastName AS empLastName, "
                             + "da.action "
                             + "FROM record r "
                             + "JOIN enrollment e ON r.enrollmentID "
                             + "= e.enrollmentID "
                             + "JOIN offense o ON r.offenseID "
                             + "= o.offenseID "
                             + "JOIN disciplinaryAction da ON r.actionID "
                             + "= da.actionID "
                             + "JOIN student s ON e.studentID "
                             + "= s.studentID "
                             + "JOIN person sp ON s.personID = sp.personID "
                             + "JOIN employee emp ON r.employeeID = "
                             + "emp.employeeID "
                             + "JOIN person ep ON emp.personID = ep.personID "
                             + "WHERE e.department = ? "
                             + "AND e.schoolYear = ? "
                             + "ORDER BY r.dateOfViolation DESC"
             )) {

            statement.setString(1, department.name());
            statement.setString(2, schoolYear);
            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    Record record = new Record();
                    record.setRecordId(rs.getLong("recordID"));
                    record.setDateOfViolation(rs.getDate("dateOfViolation"));
                    record.setDateOfResolution(rs.getDate("dateOfResolution"));
                    record.setRemarks(rs.getString("remarks"));
                    record.setStatus(RecordStatus.valueOf(rs.getString(
                            "status")));

                    Student student = new Student();
                    student.setStudentId(rs.getString("studentID"));
                    student.setFirstName(rs.getString("firstName"));
                    student.setMiddleName(rs.getString("middleName"));
                    student.setLastName(rs.getString("lastName"));

                    Enrollment enrollment = new Enrollment();
                    enrollment.setEnrollmentId(rs.getLong("enrollmentID"));
                    enrollment.setStudent(student);
                    enrollment.setSchoolYear(rs.getString("schoolYear"));
                    enrollment.setStudentLevel(rs.getString("studentLevel"));
                    enrollment.setSection(rs.getString("section"));
                    enrollment.setDepartment(Department.valueOf(rs.getString(
                            "department")));

                    record.setEnrollment(enrollment);

                    Offense offense = new Offense();
                    offense.setOffense(rs.getString("offense"));
                    offense.setType(rs.getString("type"));
                    record.setOffense(offense);

                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getString("employeeID"));
                    employee.setFirstName(rs.getString("empFirstName"));
                    employee.setLastName(rs.getString("empLastName"));
                    record.setEmployee(employee);

                    DisciplinaryAction action = new DisciplinaryAction();
                    action.setActionName(rs.getString("action"));
                    record.setAction(action);

                    records.add(record);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    @Override
    public int findTotalViolations(String schoolYear) {
        int total = 0;

        try (Connection con = ConnectionHelper.getConnection();

             PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) "
                     + "FROM record r "
                     + "JOIN enrollment e ON r.enrollmentID = e.enrollmentID "
                     + "WHERE e.schoolYear = ?"
             )) {
            stmt.setString(1, schoolYear);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return total;
    }

    /**
     * Method for current day number of violation.
     * */
    @Override
    public int findTodayViolations() {
        int total = 0;

        try (Connection con = ConnectionHelper.getConnection();

             PreparedStatement stmt = con.prepareStatement(
                     "SELECT COUNT(*) FROM record "
                             + "WHERE TRUNC(dateOfViolation) = TRUNC(SYSDATE)"
             )) {
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return total;
    }

    @Override
    public Map<String, Integer> findMostFrequentOffenses(String schoolYear) {
        Map<String, Integer> offenses = new LinkedHashMap<>();

        try (Connection con = ConnectionHelper.getConnection();

             PreparedStatement stmt = con.prepareStatement(
                     "SELECT o.offense, COUNT(*) AS total "
                             + "FROM record r "
                             + "JOIN offense o ON r.offenseID = o.offenseID "
                             + "JOIN enrollment e ON r.enrollmentID "
                             + "= e.enrollmentID "
                             + "WHERE e.schoolYear = ? "
                             + "GROUP BY o.offense "
                             + "ORDER BY total DESC"
             )) {
            stmt.setString(1, schoolYear);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    offenses.put(rs.getString("offense"), rs.getInt("total")
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return offenses;
    }
    @Override
    public List<Record> findRecordByStudentId(String studentId) {
        List<Record> records = new ArrayList<>();

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement statement = con.prepareStatement(
                     "SELECT r.recordID, r.dateOfViolation, "
                             + "r.dateOfResolution, "
                             + "r.remarks, r.status, "
                             + "e.enrollmentID, e.schoolYear, e.studentLevel, "
                             + "e.section, "
                             + "s.studentID, "
                             + "p.firstName, p.middleName, p.lastName, "
                             + "o.offense, o.type, "
                             + "da.action "
                             + "FROM record r "
                             + "JOIN enrollment e "
                             + "ON r.enrollmentID = e.enrollmentID "
                             + "JOIN student s ON e.studentID = s.studentID "
                             + "JOIN person p ON s.personID = p.personID "
                             + "JOIN offense o "
                             + "ON r.offenseID = o.offenseID "
                             + "JOIN disciplinaryAction da "
                             + "ON r.actionID = da.actionID "
                             + "WHERE s.studentID = ? "
                             + "ORDER BY r.dateOfViolation DESC"
             )) {
            statement.setString(1, studentId);
            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {
                    Record record = new Record();

                    record.setRecordId(result.getLong("recordID"));
                    record.setDateOfViolation(result.getDate(
                            "dateOfViolation"));
                    record.setDateOfResolution(
                            result.getDate("dateOfResolution"));
                    record.setRemarks(
                            result.getString("remarks"));
                    record.setStatus(
                            RecordStatus.valueOf(result.getString("status")));

                    Student student = new Student();
                    student.setStudentId(result.getString("studentID"));
                    student.setFirstName(result.getString("firstName"));
                    student.setMiddleName(result.getString("middleName"));
                    student.setLastName(result.getString("lastName"));

                    Enrollment enrollment = new Enrollment();
                    enrollment.setEnrollmentId(result.getLong("enrollmentID"));
                    enrollment.setSchoolYear(result.getString("schoolYear"));
                    enrollment.setStudentLevel(result.getString(
                            "studentLevel"));
                    enrollment.setSection(result.getString("section"));
                    enrollment.setStudent(student);
                    record.setEnrollment(enrollment);

                    Offense offense = new Offense();
                    offense.setOffense(result.getString("offense"));
                    offense.setType(
                            result.getString("type"));

                    record.setOffense(offense);


                    DisciplinaryAction disciplinaryAction
                            = new DisciplinaryAction();
                    disciplinaryAction.setActionName(result.getString(
                            "action"));
                    record.setAction(disciplinaryAction);
                    records.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;

    }

}
