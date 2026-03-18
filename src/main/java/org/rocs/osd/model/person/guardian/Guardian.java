package org.rocs.osd.model.person.guardian;

import org.rocs.osd.model.person.Person;

public class Guardian extends Person {
    private long guardianID;
    private String contactNumber;
    private Relationship relationship;

    public Guardian() {

    }
    public Guardian(long guardianID, String contactNumber, Relationship relationship, String studentID) {
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
