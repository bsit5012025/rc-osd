package org.rocs.osd.data.dao.employee.impl;

import org.rocs.osd.data.connection.ConnectionHelper;
import org.rocs.osd.data.dao.employee.EmployeeDao;
import org.rocs.osd.model.person.employee.Employee;
import org.rocs.osd.model.person.Person;
import org.rocs.osd.model.department.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeDaoImpl implements EmployeeDao {
    /**
     * Logger for logging errors and debug.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @Override
    public Employee findEmployeeByEmployeeID(String employeeID) {
        LOGGER.debug("Fetching employee data for ID: {}", employeeID);
        try (Connection conn = ConnectionHelper.getConnection();
             PreparedStatement statement = conn.prepareStatement(
                     "SELECT "
                             + "e.employeeID, "
                             + "e.department, "
                             + "e.employeeRole, "
                             + "p.personID, "
                             + "p.firstName, "
                             + "p.lastName, "
                             + "p.middleName "
                             + "FROM employee e "
                             + "JOIN person p ON e.personID = p.personID "
                             + "WHERE e.employeeID = ?")) {
            statement.setString(1, employeeID);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Person person = new Person();
                    person.setPersonID(rs.getLong("personID"));
                    person.setFirstName(rs.getString("firstName"));
                    person.setLastName(rs.getString("lastName"));
                    person.setMiddleName(rs.getString("middleName"));

                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getString("employeeID"));

                    employee.setDepartment(
                            Department.valueOf(rs.getString("department"))
                    );
                    employee.setEmployeeRole(rs.getString("employeeRole"));

                    employee.setPersonID(person.getPersonID());
                    employee.setFirstName(person.getFirstName());
                    employee.setLastName(person.getLastName());
                    employee.setMiddleName(person.getMiddleName());

                    return employee;
                } else {
                    LOGGER.warn("Employee not found: {}", employeeID);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Database error occurred while finding"
                    + " employee by ID: {}", employeeID, e);
        }
        return null;
    }

}
