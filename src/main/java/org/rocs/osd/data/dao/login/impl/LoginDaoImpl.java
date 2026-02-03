package org.rocs.osd.data.dao.login.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.model.login.Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao  {

    @Override
    public Login findLoginByUsername(String username) {
        Login login = new Login();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM LOGIN WHERE username = ?");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                login.setId(rs.getLong("id"));
                login.setUsername(rs.getString("username"));
                login.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login;
    }
}
