package org.rocs.osd.model.person;

/**
 * Represents a person with basic personal details
 * This class will serve as a base class for Student and Employee models
 */
public class Person {
    /**
     * Unique identifier for the person
     */
    private long personID;
    /**
     * Last name of the person
     */
    private String lastName;
    /**
     * Firs name of the person
     */
    private String firstName;
    /**
     * Middle name of the person
     */
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
     * Gets the unique person ID
     * @return personID
     */
    public long getPersonID() {
        return personID;
    }

    /**
     * Sets the unique person ID
     * @param personID the personID to set
     */
    public void setPersonID(long personID) {
        this.personID = personID;
    }

    /**
     * Gets the last name of the person
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the first name of the person
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name of the person
     * @return middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name of the person
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}
