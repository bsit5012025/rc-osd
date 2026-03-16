package org.rocs.osd.data.dao.guardian.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.guardian.GuardianDao;
import org.rocs.osd.model.person.guardian.Guardian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuardianDaoImpl implements GuardianDao {

    @Override
    public List<Guardian> getGuardiansByStudentID(String studentID) {

        List<Guardian> guardianList = new ArrayList<>();

        String sql = "SELECT guardianID, contactNumber, relationship, studentID FROM guardian WHERE studentID = ?";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, studentID);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Guardian guardian = new Guardian();

                    guardian.setGuardianID(rs.getString("guardianID"));
                    guardian.setContactNumber(rs.getString("contactnumber"));
                    guardian.setRelationship(rs.getString("relationship"));
                    guardian.setStudentID(rs.getString("studentID"));

                    guardianList.add(guardian);
                }

            }

        } catch (SQLException e) {
            System.out.println("SQL Exception (getGuardiansByStudentID): " + e.getMessage());
        }

        return guardianList;
    }
}

