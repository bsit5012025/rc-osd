package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;

import java.sql.*;

public class OffenseDaoImpl implements OffenseDao
{
    @Override
    public boolean editExistingViolation(String recordID, String status, String OffenseID,
                                         String remarks)
    {
        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE record SET " +
                            "status = ?, " +
                            "offenseID = ?, " +
                            "remarks = ? " +
                            "WHERE recordID = ?");

            stmt.setString(1, status);
            stmt.setString(2, OffenseID);
            stmt.setString(3, remarks);
            stmt.setString(4, recordID);
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
    public boolean editExistingDateOfViolation(String recordID, Date dateOfViolation)
    {
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

    @Override
    public boolean editExistingDateOfResolution(String recordID, Date dateOfResolution)
    {
        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE record SET " +
                            "DateOfResolution = ? " +
                            "WHERE recordID = ?");

            stmt.setDate(1, dateOfResolution);
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
