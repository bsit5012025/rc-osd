package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the OffenseDao interface.
 * This class handles offense data from the database.
 */
public class OffenseDaoImpl implements OffenseDao
{

    /**
     * Finds and retrieves an Offense object from the database by offense ID.
     * Returns an empty Offense object if no match is found.
     * @param offenseID the ID of the offense to find.
     * @return an Offense object with the offense details.
     */
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
    /**
     * Retrieves a list of all offense names from the database.
     * @return a list of offense names
     */
    @Override
    public List<String> findAllOffenseName() {
        List<String> offense = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT offense FROM offense ORDER BY offense");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                offense.add(rs.getString("offense"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offense;
    }
    @Override
    public Offense findByName(String offenseName) {

        Offense offense = null;

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT offenseID, offense, type FROM offense WHERE offense = ?");
            statement.setString(1, offenseName);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                offense = new Offense();
                offense.setOffenseId(rs.getInt("offenseID"));
                offense.setOffense(rs.getString("offense"));
                offense.setType(rs.getString("type"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offense;
    }
    @Override
    public boolean addNewOffense(Offense offense) {
        String sql = "INSERT INTO offense (offense, type, description) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, offense.getOffense());
            ps.setString(2, offense.getType());
            ps.setString(3, offense.getDescription());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        offense.setOffenseId(generatedKeys.getLong(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
        }
}
