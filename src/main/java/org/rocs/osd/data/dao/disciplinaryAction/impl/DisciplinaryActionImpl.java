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
 * DAO implementation for managing Disciplinary Action records in the Office of Student Discipline (OSD) System.
 * Provides methods to find actions by ID or name, and to retrieve all disciplinary actions.
 */
public class DisciplinaryActionImpl implements DisciplinaryActionDao {

    /**
     * Finds a disciplinary action name by its ID.
     * @param actionId the unique ID of the disciplinary action.
     * @return an Optional containing the action name if found, or Optional.empty() if not.
     */
    @Override
    public String findActionById (long actionId) {


        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT action FROM disciplinaryAction WHERE actionId = ?");
            statement.setLong(1, actionId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("action");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves a list of all disciplinary action names, sorted alphabetically.
     *
     * @return a List of action names.
     */
    @Override
    public List<String> findAllAction() {

        List<String> actions = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT action FROM disciplinaryAction ORDER BY action");
            ResultSet rs = statement.executeQuery();

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
     * @param action the name of the disciplinary action.
     * @return an Optional containing the action ID if found, or Optional.empty() if not.
     */
    @Override
    public long findActionIdByName(String action) {
        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT actionId FROM disciplinaryAction WHERE action = ?");
            statement.setString(1, action);
            ResultSet rs = statement.executeQuery();


            if (rs.next()) {
                return rs.getLong("actionId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
