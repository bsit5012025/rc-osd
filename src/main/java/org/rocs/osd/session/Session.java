package org.rocs.osd.session;

import org.rocs.osd.model.person.employee.Employee;

/**
 * Holds session data for the currently logged-in user.
 * This class is used globally across the application.
 */
public final class Session {
    /**
     * Stores the currently logged-in employee ID.
     */
    private static Employee employee;
    /**
     * Private constructor to prevent instantiation.
     */
    private Session() {
        // Default constructor
    }
    /**
     * Gets the current employee ID from session.
     * @return employee ID
     */
    public static Employee getEmployee() {
        return employee;
    }
    /**
     * Sets the current employee ID for session.
     * @param empID employee ID
     */
    public static void setEmployee(Employee empID) {
        employee = empID;
    }

}
