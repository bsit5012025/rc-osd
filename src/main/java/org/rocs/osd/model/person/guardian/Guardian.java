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
        //Intentionally empty constructor for guardian model
    }

    /**
     * Constructor with all fields.
     * @param pGuardianID unique guardian ID.
     * @param pContactNumber guardian's contact number.
     * @param pRelationship relationship to the student.
     */
    public Guardian(long pGuardianID, String pContactNumber,
                    Relationship pRelationship) {
        this.guardianID = pGuardianID;
        this.contactNumber = pContactNumber;
        this.relationship = pRelationship;
    }

    /** @return unique guardian ID. */
    public long getGuardianID() {
        return guardianID;
    }

    /** @param pGuardianID sets the unique guardian ID. */
    public void setGuardianID(long pGuardianID) {
        this.guardianID = pGuardianID;
    }

    /** @return the contact number of the guardian. */
    public String getContactNumber() {
        return contactNumber;
    }

    /** @param pContactNumber sets the guardian's contact number. */
    public void setContactNumber(String pContactNumber) {
        this.contactNumber = pContactNumber;
    }

    /** @return the relationship to the student. */
    public Relationship getRelationship() {
        return relationship;
    }

    /** @param pRelationship sets the guardian's relationship to the student. */
    public void setRelationship(Relationship pRelationship) {
        this.relationship = pRelationship;
    }
}
