package org.rocs.osd.model.department;

/**
 * Represents the different academic departments in the school.
 * Provides a display name for each department type.
 */
public enum Department {

    /** Junior High School department. */
    JHS("Junior High School"),

    /** Senior High School department. */
    SHS("Senior High School"),

    /** College department. */
    COLLEGE("College");

    /** The display name for this department. */
    private final String displayName;

    /**
     * Constructor to set the display name of the department.
     *
     * @param displayName the display name of the department
     */
    Department(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the department.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}