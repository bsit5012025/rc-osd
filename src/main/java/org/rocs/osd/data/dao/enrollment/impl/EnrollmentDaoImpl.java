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

public class EnrollmentDaoImpl implements EnrollmentDao {

    public List<Enrollment> findEnrollmentsByStudentId(String studentId){

        List<Enrollment> enrollmentList = new ArrayList<>();

        try(Connection conn = ConnectionHelper.getConnection()){
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT e.*, " +
                            "s.personID, " +
                            "s.address, " +
                            "s.departmentID AS studentDepartmentID " +
                            "FROM enrollment e " +
                            "JOIN student s ON e.studentID = s.studentID " +
                            "WHERE studentID = ? ORDER BY schoolYear DESC");
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Enrollment enrollment = new Enrollment();
                Student student = new Student();
                DisciplinaryStatus disciplinaryStatus = new DisciplinaryStatus();

                enrollment.setEnrollmentId(rs.getLong("enrollmentID"));
                enrollment.setSchoolYear(rs.getString("schoolYear"));
                enrollment.setStudentLevel(rs.getString("studentLevel"));
                enrollment.setSection(rs.getString("section"));

                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));
                student.setDepartment(Department.valueOf(rs.getString("studentDepartmentID")));
                enrollment.setStudent(student);

                enrollment.setDepartment(Department.valueOf(rs.getString("studentDepartmentID")));
                disciplinaryStatus.setDisciplinaryStatusId(rs.getLong("disciplinaryStatusID"));
                enrollment.setDisciplinaryStatus(disciplinaryStatus);

                enrollmentList.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrollmentList;
    }

    public long findEnrollmentIdByStudentId(String studentId){

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT enrollmentID " +
                            "FROM enrollment " +
                            "WHERE studentID = ? " +
                            "ORDER BY schoolYear DESC " +
                            "FETCH FIRST 1 ROW ONLY");
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getLong("enrollmentID");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public List<Enrollment> findAllLatestEnrollments() {
        List<Enrollment> studentList = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection()) {

            PreparedStatement statement = conn.prepareStatement(
                    "SELECT e.enrollmentID, e.studentID, e.studentLevel, e.section, e.department, " +
                        "s.personID, s.address, " +
                        "p.firstName, p.lastName, p.middleName " +
                        "FROM enrollment e " +
                        "JOIN student s ON e.studentID = s.studentID " +
                        "JOIN person p ON s.personID = p.personID " +
                        "WHERE e.schoolYear = (SELECT MAX(e2.schoolYear) FROM enrollment e2 WHERE e2.studentID = e.studentID )");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Enrollment enrollment = new Enrollment();
                Student student = new Student();

                enrollment.setEnrollmentId(rs.getLong("enrollmentID"));
                enrollment.setStudentLevel(rs.getString("studentLevel"));
                enrollment.setSection(rs.getString("section"));
                enrollment.setDepartment(Department.valueOf(rs.getString("department")));

                student.setStudentId(rs.getString("studentID"));
                student.setPersonID(rs.getLong("personID"));
                student.setAddress(rs.getString("address"));

                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setMiddleName(rs.getString("middleName"));

                enrollment.setStudent(student);
                studentList.add(enrollment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }
}
