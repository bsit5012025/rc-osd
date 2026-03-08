package org.rocs.osd.model.person;

/**
 * Represents a person record in the Office of Student Discipline System.
 * Stores basic personal details including ID, first name, last name, and middle name.
 * This class serves as a base class for Student and Employee models.
 */
public class Person {

    private long personID;
    private String lastName;
    private String firstName;
    private String middleName;

    /**
     * Default constructor
     */
    public Person() {
    }

    /**
     * @param personID   unique identifier of the person
     * @param lastName   last name of the person
     * @param firstName  first name of the person
     * @param middleName middle name of the person
     */
    public Person(long personID, String lastName, String firstName, String middleName) {
        this.personID = personID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    /**
     * Gets the unique person ID.
     *
     * @return  the personID.
     */
    public long getPersonID() {
        return personID;
    }

    /**
     * Sets the unique person ID.
     *
     * @param personID  the personID to set.
     */
    public void setPersonID(long personID) {
        this.personID = personID;
    }

    /**
     * Gets the last name of the person.
     *
     * @return  the lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     *
     * @param lastName  the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the first name of the person.
     *
     * @return  the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     *
     * @param firstName  the firstName to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name of the person.
     *
     * @return  the middleName.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name of the person.
     *
     * @param middleName  the middleName to set.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
