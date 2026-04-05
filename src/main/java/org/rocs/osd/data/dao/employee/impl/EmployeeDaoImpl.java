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

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public Employee findEmployeeByEmployeeID(String employeeID) {
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
