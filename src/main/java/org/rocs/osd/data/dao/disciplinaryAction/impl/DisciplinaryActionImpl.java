package org.rocs.osd.data.dao.disciplinaryAction.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.disciplinaryAction.DisciplinaryActionDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO implementation for managing disciplinary actions in the Office of Student
 * Discipline System. Provides methods to find actions by ID or name and
 * retrieve all actions.
 */
public class DisciplinaryActionImpl implements DisciplinaryActionDao {

    /**
     * Finds the name of a disciplinary action by its ID.
     * @param actionId the unique ID of the disciplinary action.
     * @return the action name if found; null if not found.
     */
    @Override
    public String findActionById(long actionId) {
        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT action FROM disciplinaryAction WHERE actionId = ?"
            );
            stmt.setLong(1, actionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("action");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        List<String> actions = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT action FROM disciplinaryAction ORDER BY action"
            );
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                actions.add(rs.getString("action"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT actionId FROM disciplinaryAction WHERE action = ?"
            );
            stmt.setString(1, action);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("actionId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
