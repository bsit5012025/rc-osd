package org.rocs.osd.model.person;

public class Person {
    private long personID;
    private String lastName;
    private String firstName;
    private String middleName;

    public Person() {
    }

    // Constructor with all values
    public Person(long personID, String lastName, String firstName, String middleName) {
        this.personID = personID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    //This will get the Person ID
    public long getPersonID() {
        return personID;
    }

    //This will set the Person ID
    public void setPersonID(long personID) {
        this.personID = personID;
    }

    //This will get the Last Name
    public String getLastName() {
        return lastName;
    }

    //This will set the Last Name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //This will get the First Name
    public String getFirstName() {
        return firstName;
    }

    //This will set the First Name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //This will get the Middle Name
    public String getMiddleName() {
        return middleName;
    }

    //This will set the Middle Name
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
