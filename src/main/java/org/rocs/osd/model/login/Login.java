package org.rocs.osd.model.login;

import org.rocs.osd.model.person.Person;

/**
 * Represents a user's login credentials in the OSD System.
 * Stores ID, username, password, and associated person info.
 */
public class Login {

    /** Unique ID of the login record. */
    private long id;

    /** Username used to access the system. */
    private String username;

    /** Password associated with the username. */
    private String password;

    /** Person linked to this login. */
    private Person person;

    /** Default constructor. */
    public Login() {
    }

    /**
     * Constructor with all fields.
     * @param id unique ID of the login record.
     * @param username username for login.
     * @param password password for the username.
     * @param person the person associated with this login.
     */
    public Login(long id, String username, String password, Person person) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.person = person;
    }

    /** @return unique ID of the login record. */
    public long getId() {
        return id;
    }

    /** @param id sets the unique ID of the login record. */
    public void setId(long id) {
        this.id = id;
    }

    /** @return the username. */
    public String getUsername() {
        return username;
    }

    /** @param username sets the username. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return the password. */
    public String getPassword() {
        return password;
    }

    /** @param password sets the password. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @return the person associated with this login. */
    public Person getPerson() {
        return person;
    }

    /** @param person sets the person associated with this login. */
    public void setPerson(Person person) {
        this.person = person;
    }
}