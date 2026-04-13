package org.rocs.osd.session;

/**
 * Holds session data for the currently logged-in user.
 * This class is used globally across the application.
 */
public final class Session {
    /**
     * Stores the currently logged-in employee ID.
     */
    private static String employeeId;
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
    public static String getEmployeeId() {
        return employeeId;
    }
    /**
     * Sets the current employee ID for session.
     * @param empId employee ID
     */
    public static void setEmployeeId(String empId) {
        employeeId = empId;
    }

}
