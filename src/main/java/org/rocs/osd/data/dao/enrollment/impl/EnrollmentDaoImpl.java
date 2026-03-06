package org.rocs.osd.data.dao.enrollment.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.enrollment.EnrollmentDao;
import org.rocs.osd.model.enrollment.Enrollment;

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
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM enrollment WHERE studentID = ? ORDER BY schoolYear DESC");
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Enrollment enrollment = new Enrollment();

                enrollment.setEnrollmentId(rs.getLong("enrollmentID"));
                enrollment.setStudentId(rs.getString("studentID"));
                enrollment.setSchoolYear(rs.getString("schoolYear"));
                enrollment.setStudentLevel(rs.getString("studentLevel"));
                enrollment.setSection(rs.getString("section"));
                enrollment.setDepartmentId(rs.getString("departmentID"));
                enrollment.setDisciplinaryStatusId(rs.getString("disciplinaryStatusID"));

                enrollmentList.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enrollmentList;
    }

    public long findEnrollmentIdByStudentId(String studentId){

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT enrollmentID " +
                    "FROM enrollment WHERE studentID = ?" +
                    "ORDER BY schoolYear DESC" +
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
}
