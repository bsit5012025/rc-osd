package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OffenseDaoImpl implements OffenseDao
{

    @Override
    public Offense findOffenseById(String offenseID)
    {
        Offense offense = new Offense();
        try (Connection conn = ConnectionHelper.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT " +
                            "o.offenseID," +
                            "o.offense," +
                            "o.type," +
                            "o.description" +
                            "FROM offense o where offenseID = ?");

            statement.setString(1, offenseID);
            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                offense.setOffenseId(rs.getLong("offenseID"));
                offense.setOffense(rs.getString("offense"));
                offense.setType(rs.getString("type"));
                offense.setDescription(rs.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offense;
    }
}
