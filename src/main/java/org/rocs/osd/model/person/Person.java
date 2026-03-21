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
    }

    /**
     * Constructor with all fields.
     *
     * @param personID unique identifier of the person.
     * @param lastName last name of the person.
     * @param firstName first name of the person.
     * @param middleName middle name of the person.
     */
    public Person(long personID, String lastName,
                  String firstName, String middleName) {
        this.personID = personID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    /** @return the unique person ID. */
    public long getPersonID() {
        return personID;
    }

    /** @param personID sets the unique person ID. */
    public void setPersonID(long personID) {
        this.personID = personID;
    }

    /** @return the last name. */
    public String getLastName() {
        return lastName;
    }

    /** @param lastName sets the last name. */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** @return the first name. */
    public String getFirstName() {
        return firstName;
    }

    /** @param firstName sets the first name. */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /** @return the middle name. */
    public String getMiddleName() {
        return middleName;
    }

    /** @param middleName sets the middle name. */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}