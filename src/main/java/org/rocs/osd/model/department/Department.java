package org.rocs.osd.model.department;

/**
 * Represents the different departments within the Office of Student Discipline System.
 * Each department has a display name.
 */
public enum Department {
    JHS("Junior High School"),
    SHS("Senior High School"),
    COLLEGE("College");

    private final String displayName;

    /**
     * Sets the name for the department.
     * @param displayName name of the department.
     */
    Department(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the name of the department.
     * @return department name.
     */
    public String getDisplayName() {
        return displayName;
    }

}
