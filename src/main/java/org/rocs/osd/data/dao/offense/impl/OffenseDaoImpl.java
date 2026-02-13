package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;
import org.rocs.osd.model.person.student.Student;
import org.rocs.osd.model.record.Record;

import java.sql.*;
import java.util.ArrayList;

public class OffenseDaoImpl implements OffenseDao
{
    @Override
    public boolean editExistingViolation(String recordID, String status, String OffenseID,
                                         String remarks, Date dateOfResolution)
    {
        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE record SET " +
                            "status = ?, " +
                            "offenseID = ?, " +
                            "remarks = ?, " +
                            "DateOfResolution = ? " +
                            "WHERE recordID = ?");

            stmt.setString(1, status);
            stmt.setString(2, OffenseID);
            stmt.setString(3, remarks);
            stmt.setDate(4, dateOfResolution);
            stmt.setString(5, recordID);
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
    public boolean editExistingDateOfViolation(String recordID, Date dateOfViolation) {
        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE record SET " +
                            "DateOfViolation = ? " +
                            "WHERE recordID = ?");

            stmt.setDate(1, dateOfViolation);
            stmt.setString(2, recordID);
            stmt.executeUpdate();

            return true;
        }
        catch (SQLException e)
        {
            System.out.println("An SQL Exception occurred." + e.getMessage());

            return false;
        }
    }
}
