package org.rocs.osd.model.person.guardian;

import org.rocs.osd.model.person.Person;

/**
 * Represents a guardian in the OSD System.
 * Inherits from Person and includes guardian ID,
 * contact number, and relationship.
 */
public class Guardian extends Person {

    /** Unique ID of the guardian. */
    private long guardianID;

    /** Contact number of the guardian. */
    private String contactNumber;

    /** Relationship of the guardian to the student. */
    private Relationship relationship;

    /** Default constructor. */
    public Guardian() {
    }

    /**
     * Constructor with all fields.
     * @param guardianID unique guardian ID.
     * @param contactNumber guardian's contact number.
     * @param relationship relationship to the student.
     */
    public Guardian(long guardianID, String contactNumber,
                    Relationship relationship) {
        this.guardianID = guardianID;
        this.contactNumber = contactNumber;
        this.relationship = relationship;
    }

    /** @return unique guardian ID. */
    public long getGuardianID() {
        return guardianID;
    }

    /** @param guardianID sets the unique guardian ID. */
    public void setGuardianID(long guardianID) {
        this.guardianID = guardianID;
    }

    /** @return the contact number of the guardian. */
    public String getContactNumber() {
        return contactNumber;
    }

    /** @param contactNumber sets the guardian's contact number. */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /** @return the relationship to the student. */
    public Relationship getRelationship() {
        return relationship;
    }

    /** @param relationship sets the guardian's relationship to the student. */
    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }
}