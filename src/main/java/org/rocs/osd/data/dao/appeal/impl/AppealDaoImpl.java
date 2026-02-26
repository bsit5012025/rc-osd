package org.rocs.osd.data.dao.appeal.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.appeal.AppealDao;
import org.rocs.osd.model.appeal.Appeal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppealDaoImpl implements AppealDao {

    @Override
    public Appeal saveAppeal(Appeal appeal) {

        String sql = "INSERT INTO appeal (recordID, enrollmentID, message, dateFiled, status) " +
                "VALUES (?, ?, ?, SYSDATE, ?)";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql, new String[]{"appealID"})) {

            preparedStatement.setLong(1, appeal.getRecordID());
            preparedStatement.setLong(2, appeal.getEnrollmentID());
            preparedStatement.setString(3, appeal.getAppealMessage());
            preparedStatement.setString(4, appeal.getAppealStatus());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    appeal.setAppealID(rs.getLong(1));
                }
            }

            return appeal;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Appeal findByAppealId(Long appealId) {

        String sql = "SELECT * FROM appeal WHERE appealID = ?";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, appealId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Appeal> findByEnrollmentId(Long enrollmentId) {

        String sql = "SELECT * FROM appeal WHERE enrollmentID = ?";

        List<Appeal> appeals = new ArrayList<>();

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, enrollmentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    appeals.add(mapRow(resultSet));
                }
            }

            return appeals;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAppealStatus(Long appealId, String status) {

        String sql = "UPDATE appeal SET status = ? WHERE appealID = ?";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, status);
            preparedStatement.setLong(2, appealId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Appeal mapRow(ResultSet resultSet) throws SQLException {

        Appeal appeal = new Appeal();
        appeal.setAppealID(resultSet.getLong("appealID"));
        appeal.setRecordID(resultSet.getLong("recordID"));
        appeal.setEnrollmentID(resultSet.getLong("enrollmentID"));
        appeal.setMessage(resultSet.getString("message"));
        appeal.setDateFiled(resultSet.getDate("dateFiled"));
        appeal.setStatus(resultSet.getString("status"));

        return appeal;
    }
}