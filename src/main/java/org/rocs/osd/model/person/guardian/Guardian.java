package org.rocs.osd.model.person.guardian;

import org.rocs.osd.model.person.Person;

/**
 * Represents a student's guardian in the Office of Student Discipline System.
 * Extends the base Person class and adds guardian-specific information.
 */
public class Guardian extends Person {
    private long guardianID;
    private String contactNumber;
    private Relationship relationship;

    /**
     * Default constructor
      */
    public Guardian() {

    }
    /**
     * Constructs a Guardian object with the specified ID, contact number, and relationship.
     * @param guardianID unique ID of the guardian.
     * @param contactNumber guardian's contact number.
     * @param relationship relationship to the student.
     */
    public Guardian(long guardianID, String contactNumber, Relationship relationship) {
        this.guardianID = guardianID;
        this.contactNumber = contactNumber;
        this.relationship = relationship;
    }

    public long getGuardianID() {
        return guardianID;
    }

    public void setGuardianID(long guardianID) {
        this.guardianID = guardianID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }
}
