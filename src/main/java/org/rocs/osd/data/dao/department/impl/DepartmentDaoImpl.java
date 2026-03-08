package org.rocs.osd.data.dao.department.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.department.DepartmentDao;
import org.rocs.osd.model.department.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the DepartmentDao interface.
 * This class handles department data from the database.
 */
public class DepartmentDaoImpl implements DepartmentDao {

    /**
     * Finds and retrieves a Department object from the database by ID.
     * Returns an empty Department object if no match is found.
     */
    @Override
    public Department findDepartmentById(long departmentId) {

        Department department = new Department();
        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM department WHERE departmentId = ?");
            statement.setLong(1, departmentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                department.setDepartmentId(rs.getLong("departmentId"));
                department.setDepartmentName(rs.getString("departmentName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return department;
    }

    /**
     * Retrieves a list of all department names from the database.
     */
    @Override
    public List<String> findAllDepartmentName() {
        List<String> departmentNames = new ArrayList<>();

        try (Connection conn = ConnectionHelper.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT departmentName FROM department ORDER BY departmentName");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                departmentNames.add(rs.getString("departmentName"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentNames;
    }

}
