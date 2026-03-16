package org.rocs.osd.model.person.guardian;

import org.rocs.osd.model.person.Person;

public class Guardian extends Person {
    private String guardianID;
    private String contactNumber;
    private String relationship;
    private String studentID;

    public Guardian() {

    }
    public Guardian(String guardianID, String contactNumber, String relationship, String studentID) {
        this.guardianID = guardianID;
        this.contactNumber = contactNumber;
        this.relationship = relationship;
        this.studentID = studentID;
    }

    public String getGuardianID() {
        return guardianID;
    }

    public void setGuardianID(String guardianID) {
        this.guardianID = guardianID;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
