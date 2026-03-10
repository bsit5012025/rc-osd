package org.rocs.osd.data.dao.request.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.request.RequestDao;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.request.Request;
import org.rocs.osd.model.request.RequestStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDaoImpl implements RequestDao {

    @Override
    public void setRequest(Employee employee, String details, String message, String type) {
        try (Connection con = ConnectionHelper.getConnection()) {
            String sql = "INSERT INTO request (employeeID, details, type, message, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, details);
            stmt.setString(3, type);
            stmt.setString(4, message);
            stmt.setString(5, RequestStatus.PENDING.toString());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An SQL Exception occurred. " + e.getMessage());
        }
    }

    @Override
    public List<Request> getAllRequests() {
        List<Request> requestList = new ArrayList<>();

        String sql = "SELECT * FROM REQUEST";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Request r = new Request();
                Employee emp = new Employee();
                r.setRequestID(rs.getLong("requestID"));
                emp.setEmployeeId(rs.getString("employeeID"));
                r.setEmployee(emp);

                r.setDetails(rs.getString("details"));
                r.setType(rs.getString("type"));
                r.setMessage(rs.getString("message"));
                r.setStatus(RequestStatus.valueOf(rs.getString("status")));

                requestList.add(r);
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception (getAllRequests): " + e.getMessage());
        }

        return requestList;
    }

    @Override
    public boolean updateRequestStatus(long requestID, RequestStatus status) {
        String sql = "UPDATE REQUEST SET status = ? WHERE requestID = ?";

        try (Connection con = ConnectionHelper.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, status.toString());
            stmt.setString(2, String.valueOf(requestID));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("SQL Exception (updateRequestStatus): " + e.getMessage());
        }

        return false;
    }
}
