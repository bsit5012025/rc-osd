package org.rocs.osd.data.dao.guardian.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.guardian.GuardianDao;
import org.rocs.osd.model.person.guardian.Guardian;
import org.rocs.osd.model.person.guardian.Relationship;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.person.student.guardian.StudentGuardian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO implementation for managing guardian information in the Office of
 * Student Discipline System.
 *<p>
 * Provides methods to retrieve guardians associated with students.
 */
public class GuardianDaoImpl implements GuardianDao {
    /**
     * Logger for logging errors and debug.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(GuardianDaoImpl.class);

    /**
     * Finds guardians for a given student by student ID.
     *
     * @param pStudentId the ID of the student.
     * @return a list of StudentGuardian objects linking the student with their
     *         guardians. Returns an empty list if none found.
     */
    @Override
    public List<StudentGuardian> findGuardianByStudentId(String pStudentId) {

        List<StudentGuardian> sgList = new ArrayList<>();

        String sql = """
                SELECT g.*, sg.studentID,
                       p.*
                FROM guardian g
                JOIN person p ON g.personID = p.personID
                JOIN studentGuardian sg ON g.guardianID = sg.guardianID
                WHERE sg.studentID = ?
                """;

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pStudentId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Guardian guardian = new Guardian();
                    guardian.setGuardianID(rs.getLong("guardianID"));
                    guardian.setContactNumber(rs.getString("contactNumber"));
                    guardian.setRelationship(
                            Relationship.valueOf(rs.getString(
                                    "relationship").toUpperCase(Locale.ROOT))
                    );
                    guardian.setFirstName(rs.getString("firstName"));
                    guardian.setLastName(rs.getString("lastName"));

                    Student student = new Student();
                    student.setStudentId(rs.getString("studentID"));

                    StudentGuardian sg = new StudentGuardian();
                    sg.setStudent(student);
                    sg.setGuardian(guardian);
                    sgList.add(sg);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception occurred while finding guardian"
                    + " for student ID: {}", pStudentId, e);
        }

        return sgList;
    }
}
