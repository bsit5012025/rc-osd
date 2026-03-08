package org.rocs.osd.data.dao.disciplinaryAction.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.disciplinaryAction.DisciplinaryActionDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaryActionImpl implements DisciplinaryActionDao {

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

    @Override
    public long findActionIdByName(String action) {
        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT actionId FROM disciplinaryAction WHERE action = ?");
            ResultSet rs = statement.executeQuery();
            statement.setString(1, action);

            if (rs.next()) {
                return rs.getLong("actionId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
