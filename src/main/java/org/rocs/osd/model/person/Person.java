package org.rocs.osd.model.person;

/**
 * Represents a person record in the Office of Student Discipline System.
 * Stores basic personal details including ID, first name, last name, and middle
 * name. Serves as a base class for Student and Employee.
 */
public class Person {

    /** Unique identifier for the person. */
    private long personID;

    /** Last name of the person. */
    private String lastName;

    /** First name of the person. */
    private String firstName;

    /** Middle name of the person. */
    private String middleName;

    /** Default constructor. */
    public Person() {
        // Default constructor
    }

    /**
     * Constructor with all fields.
     *
     * @param pPersonID unique identifier of the person.
     * @param pLastName last name of the person.
     * @param pFirstName first name of the person.
     * @param pMiddleName middle name of the person.
     */
    public Person(long pPersonID, String pLastName,
                  String pFirstName, String pMiddleName) {
        this.personID = pPersonID;
        this.lastName = pLastName;
        this.firstName = pFirstName;
        this.middleName = pMiddleName;
    }

    /** @return the unique person ID. */
    public long getPersonID() {
        return personID;
    }

    /** @param pPersonID sets the unique person ID. */
    public void setPersonID(long pPersonID) {
        this.personID = pPersonID;
    }

    /** @return the last name. */
    public String getLastName() {
        return lastName;
    }

    /** @param pLastName sets the last name. */
    public void setLastName(String pLastName) {
        this.lastName = pLastName;
    }

    /** @return the first name. */
    public String getFirstName() {
        return firstName;
    }

    /** @param pFirstName sets the first name. */
    public void setFirstName(String pFirstName) {
        this.firstName = pFirstName;
    }

    /** @return the middle name. */
    public String getMiddleName() {
        return middleName;
    }

    /** @param pMiddleName sets the middle name. */
    public void setMiddleName(String pMiddleName) {
        this.middleName = pMiddleName;
    }
}
