package org.rocs.osd.data.dao.request.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the RequestDao interface.
 * This class handles database operations for Request objects including:
 * adding a new request, fetching all requests, and updating request status.
 */
public class RequestDaoImpl implements RequestDao {

    /**
     * Adds a new request to the database with status PENDING.
     * @param employeeID the ID of the employee creating the request.
     * @param details the details of the request.
     * @param message the message or reason for the request.
     * @param type the type/category of the request.
     * @return true if the request was successfully added, false otherwise.
     */
    @Override
    public boolean addRequest(String employeeID, String details, String message,
                              String type) {
        try (Connection con = ConnectionHelper.getConnection()) {
            String sql =
                    "INSERT INTO request (employeeID, " +
                            "details, " +
                            "type, " +
                            "message, " +
                            "status) " +
                            "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, employeeID);
            stmt.setString(2, details);
            stmt.setString(3, type);
            stmt.setString(4, message);
            stmt.setString(5, RequestStatus.PENDING.toString());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred. " + e.getMessage());
        }

        return false;
    }

    /**
     * Retrieves all requests from the database.
     * Each record from the REQUEST table is mapped to a Request object.
     *
     * @return a List of Request objects representing all requests
     */
    @Override
    public List<Request> findAllRequests() {
        List<Request> requestList = new ArrayList<>();

        String sql = "SELECT * FROM REQUEST";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Request r = new Request();
                r.setRequestID(rs.getLong("requestID"));
                r.setEmployeeID(rs.getString("employeeID"));
                r.setDetails(rs.getString("details"));
                r.setType(rs.getString("type"));
                r.setMessage(rs.getString("message"));
                r.setStatus(RequestStatus.valueOf(rs.getString("status")));

                requestList.add(r);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception (getAllRequests): "
            + e.getMessage());
        }

        return requestList;
    }

    /**
     * Updates the status of a request in the database.
     * @param requestID the ID of the request to update.
     * @param status the new status to assign to the request.
     * @return true if the update was successful, false otherwise.
     */
    @Override
    public boolean updateRequestStatus(long requestID, RequestStatus status) {
        String sql = "UPDATE REQUEST SET status = ? WHERE requestID = ?";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, status.toString());
            stmt.setString(2, String.valueOf(requestID));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SQL Exception (updateRequestStatus): "
            + e.getMessage());
        }
        return false;
    }
}