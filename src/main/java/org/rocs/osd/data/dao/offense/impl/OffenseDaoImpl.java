package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;

import java.sql.*;

public class OffenseDaoImpl implements OffenseDao
{
    @Override
    public boolean editExistingViolation(long recordID, String status, long OffenseID,
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
            stmt.setLong(2, OffenseID);
            stmt.setString(3, remarks);
            stmt.setLong(4, recordID);
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
    public boolean editExistingDateOfViolation(long recordID, Date dateOfViolation)
    {
        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE record SET " +
                            "DateOfViolation = ? " +
                            "WHERE recordID = ?");

            stmt.setDate(1, dateOfViolation);
            stmt.setLong(2, recordID);
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
    public boolean editExistingDateOfResolution(long recordID, Date dateOfResolution)
    {
        try (Connection con = ConnectionHelper.getConnection())
        {
            PreparedStatement stmt = con.prepareStatement(
                    "UPDATE record SET " +
                            "DateOfResolution = ? " +
                            "WHERE recordID = ?");

            stmt.setDate(1, dateOfResolution);
            stmt.setLong(2, recordID);
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
