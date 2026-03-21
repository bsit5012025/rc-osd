package org.rocs.osd.data.dao.offense.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.offense.OffenseDao;
import org.rocs.osd.model.offense.Offense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the OffenseDao interface.
 * This class handles offense data from the database, including
 * retrieval, addition, and lookup by ID or name.
 */
public class OffenseDaoImpl implements OffenseDao {

    /**
     * Finds and retrieves an Offense object from the database by offense ID.
     * Returns an empty Offense object if no match is found.
     * @param offenseID the ID of the offense to find.
     * @return an Offense object with the offense details.
     */
    @Override
    public Offense findOffenseById(String offenseID) {
        Offense offense = new Offense();

        String sql = "SELECT o.offenseID, o.offense, o.type, o.description " +
                "FROM offense o WHERE o.offenseID = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, offenseID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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
     * Retrieves all offense names from the database in alphabetical order.
     * @return a list of offense names.
     */
    @Override
    public List<String> findAllOffenseName() {
        List<String> offenses = new ArrayList<>();
        String sql = "SELECT offense FROM offense ORDER BY offense";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                offenses.add(rs.getString("offense"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offenses;
    }

    /**
     * Finds an Offense object by its name.
     * @param offenseName the name of the offense to find.
     * @return an Offense object with details, or null if not found.
     */
    @Override
    public Offense findByName(String offenseName) {
        Offense offense = null;
        String sql = "SELECT offenseID, " +
                "offense, " +
                "type FROM offense WHERE offense = ?";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, offenseName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                offense = new Offense();
                offense.setOffenseId(rs.getLong("offenseID"));
                offense.setOffense(rs.getString("offense"));
                offense.setType(rs.getString("type"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offense;
    }

    /**
     * Adds a new offense to the database.
     * @param offense the Offense object to add.
     * @return true if the offense was successfully added, false otherwise.
     */
    @Override
    public boolean addNewOffense(Offense offense) {
        String sql = "INSERT INTO offense " +
                "(offense, " +
                "type, " +
                "description)" +
                " VALUES (?, ?, ?)";

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, offense.getOffense());
            ps.setString(2, offense.getType());
            ps.setString(3, offense.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}