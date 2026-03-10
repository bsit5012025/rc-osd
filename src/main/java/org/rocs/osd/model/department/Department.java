package org.rocs.osd.model.department;

public enum Department {
    JHS("Junior High School"),
    SHS("Senior High School"),
    COLLEGE("College");

    private final String displayName;

    Department(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
