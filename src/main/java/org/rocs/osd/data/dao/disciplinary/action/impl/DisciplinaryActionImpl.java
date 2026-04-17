package org.rocs.osd.data.dao.disciplinary.action.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.disciplinary.action.DisciplinaryActionDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DAO implementation for managing disciplinary actions in the Office of Student
 * Discipline System. Provides methods to find actions by ID or name and
 * retrieve all actions.
 */
public class DisciplinaryActionImpl implements DisciplinaryActionDao {
    /**
     * Logger for logging errors and debug.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(DisciplinaryActionImpl.class);
    /**
     * Finds the name of a disciplinary action by its ID.
     *
     * @param actionId the unique ID of the disciplinary action.
     * @return the action name if found; null if not found.
     */
    @Override
    public String findActionById(long actionId) {
        LOGGER.debug("Attempting to find action name for ID: {}", actionId);
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT action FROM disciplinaryAction WHERE actionId = ?"
             )) {
            stmt.setLong(1, actionId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("action");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot fetch action by ID due "
                    + "to a database error: {}", actionId, e);
            throw new RuntimeException(
                    "Error querying disciplinary action by ID", e);
        }

        return null;
    }

    /**
     * Retrieves all disciplinary action names from the database,
     * sorted alphabetically.
     *
     * @return a list of all action names
     */
    @Override
    public List<String> findAllAction() {
        LOGGER.debug("Retrieving all disciplinary actions from database.");
        List<String> actions = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT action FROM disciplinaryAction ORDER BY action"
             )) {
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    actions.add(rs.getString("action"));
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Successfully retrieved {} disciplinary "
                            + "actions.", actions.size());
                }
            }
        } catch (SQLException e) {
            LOGGER.error(
                    "Failed to retrieve disciplinary actions from database", e);
            throw new RuntimeException(
                    "Error fetching all disciplinary actions", e);
        }

        return actions;
    }

    /**
     * Finds the ID of a disciplinary action by its name.
     *
     * @param action the name of the disciplinary action.
     * @return the action ID if found; -1 if not found.
     */
    @Override
    public long findActionIdByName(String action) {
        LOGGER.debug("Searching for action ID with name: {}", action);
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT actionId FROM disciplinaryAction WHERE action = ?"
             )) {
            stmt.setString(1, action);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("actionId");
                }
            }
        } catch (SQLException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Searching for action name'{}'"
                        + " has failed: {}", action, e.getMessage(), e);
            }
        }
        return -1;
    }
}
