package org.rocs.osd.data.dao.enrollment.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.model.department.Department;
import org.rocs.osd.model.disciplinaryStatus.DisciplinaryStatus;
import org.rocs.osd.model.enrollment.Enrollment;
import org.rocs.osd.model.person.student.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the EnrollmentDao interface.
 * Handles retrieval of student enrollment information from the
 * database in the Office of Student Discipline System.
 */
public class EnrollmentDaoImpl implements EnrollmentDao {

    /**
     * Finds all enrollments of a student by their student ID.
     *
     * @param studentId the ID of the student
     * @return a list of Enrollment objects, sorted by school year
     *         in descending order. Returns an empty list if none found.
     */
    public List<Enrollment> findEnrollmentsByStudentId(String studentId) {

        List<Enrollment> enrollmentList = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT e.*, s.personID, s.address, s.studentType, "
                            + "s.departmentID AS studentDepartmentID, "
                            + "ds.status AS disciplinaryStatusName "
                            + "FROM enrollment e "
                            + "LEFT JOIN disciplinaryStatus ds "
                            + "ON e.disciplinaryStatusID "
                            + "= ds.disciplinaryStatusID "
                            + "JOIN student s ON e.studentID = s.studentID "
                            + "WHERE studentID = ? "
                            + "ORDER BY schoolYear DESC"
            ); statement.setString(
                    1, studentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                Student student = new Student();
                DisciplinaryStatus status = new DisciplinaryStatus();

                enrollment.setEnrollmentId(rs.getLong(
                        "enrollmentID"));
                enrollment.setSchoolYear(rs.getString(
                        "schoolYear"));
                enrollment.setStudentLevel(rs.getString(
                        "studentLevel"));
                enrollment.setSection(rs.getString(
                        "section"));

                student.setStudentId(rs.getString(
                        "studentID"));
                student.setPersonID(rs.getLong(
                        "personID"));
                student.setStudentType(rs.getString(
                        "studentType"));
                student.setAddress(rs.getString(
                        "address"));
                student.setDepartment(Department.valueOf(rs.getString(
                        "studentDepartmentID")));
                enrollment.setStudent(student);

                enrollment.setDepartment(Department.valueOf(rs.getString(
                        "studentDepartmentID")));
                status.setDisciplinaryStatusId(rs.getLong(
                        "disciplinaryStatusID"));
                status.setStatus(rs.getString("disciplinaryStatusName"));
                enrollment.setDisciplinaryStatus(status);

                enrollmentList.add(enrollment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrollmentList;
    }

    /**
     * Retrieves the latest enrollment ID for a given student.
     *
     * @param studentId the student ID
     * @return the latest enrollment ID, or -1 if not found
     */
    public long findEnrollmentIdByStudentId(String studentId) {

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT enrollmentID "
                            + "FROM enrollment "
                            + "WHERE studentID = ? "
                            + "ORDER BY schoolYear DESC "
                            + "FETCH FIRST 1 ROW ONLY"
            );
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getLong(
                        "enrollmentID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Retrieves all latest enrollments for all students.
     *
     * @return a list of Enrollment objects representing the latest
     *         enrollment of each student
     */
    public List<Enrollment> findAllLatestEnrollments() {
        List<Enrollment> enrollmentList = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT e.enrollmentID, e.studentID, e.studentLevel,"
                            + " e.section, e.department, e.schoolYear, "
                            + "s.personID, s.address, s.studentType, "
                            + "p.firstName, p.lastName,"
                            + "p.middleName, "
                            + "ds.disciplinaryStatusID,"
                            + "ds.status, ds.description "
                            + "FROM enrollment e "
                            + "JOIN student s ON e.studentID = s.studentID "
                            + "JOIN person p ON s.personID = p.personID "
                            + "LEFT JOIN disciplinaryStatus ds "
                            + "ON e.disciplinaryStatusID "
                            + "= ds.disciplinaryStatusID "
                            + "WHERE e.schoolYear = ("
                            + "SELECT MAX(e2.schoolYear) "
                            + "FROM enrollment e2 "
                            + "WHERE e2.studentID = e.studentID)"
            );
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Enrollment enrollment = new Enrollment();
                Student student = new Student();
                DisciplinaryStatus status = new DisciplinaryStatus();

                enrollment.setEnrollmentId(rs.getLong(
                        "enrollmentID"));
                enrollment.setStudentLevel(rs.getString(
                        "studentLevel"));
                enrollment.setSection(rs.getString(
                        "section"));
                enrollment.setDepartment(Department.valueOf(rs.getString(
                        "department")));
                enrollment.setSchoolYear(rs.getString(
                        "schoolYear"));
                student.setStudentId(rs.getString(
                        "studentID"));
                student.setPersonID(rs.getLong(
                        "personID"));
                student.setAddress(rs.getString(
                        "address"));
                student.setStudentType(rs.getString(
                        "studentType"));
                student.setFirstName(rs.getString(
                        "firstName"));
                student.setLastName(rs.getString(
                        "lastName"));
                student.setMiddleName(rs.getString(
                        "middleName"));
                status.setDisciplinaryStatusId(rs.getLong(
                        "disciplinaryStatusID"));
                status.setStatus(rs.getString(
                        "status"));
                status.setDescription(rs.getString(
                        "description"));

                enrollment.setDisciplinaryStatus(status);
                enrollment.setStudent(student);
                enrollmentList.add(enrollment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrollmentList;
    }
}
