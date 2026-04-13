package org.rocs.osd.model.login;

import org.rocs.osd.model.person.Person;
import org.rocs.osd.model.person.employee.Employee;

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

    /** Employee linked to this login. */
    private Employee employee;

    /** Default constructor. */
    public Login() {
        // Default constructor
    }

    /**
     * Constructor with all fields.
     * @param pId unique ID of the login record.
     * @param pUsername username for login.
     * @param pPassword password for the username.
     * @param pPerson the person associated with this login.
     */
    public Login(long pId, String pUsername, String pPassword, Person pPerson) {
        this.id = pId;
        this.username = pUsername;
        this.password = pPassword;
        this.person = pPerson;
    }

    /** @return unique ID of the login record. */
    public long getId() {
        return id;
    }

    /** @param pId sets the unique ID of the login record. */
    public void setId(long pId) {
        this.id = pId;
    }

    /** @return the username. */
    public String getUsername() {
        return username;
    }

    /** @param pUsername sets the username. */
    public void setUsername(String pUsername) {
        this.username = pUsername;
    }

    /** @return the password. */
    public String getPassword() {
        return password;
    }

    /** @param pPassword sets the password. */
    public void setPassword(String pPassword) {
        this.password = pPassword;
    }

    /** @return the person associated with this login. */
    public Person getPerson() {
        return person;
    }

    /** @param pPerson sets the person associated with this login. */
    public void setPerson(Person pPerson) {
        this.person = pPerson;
    }

    /** @param emp sets the person associated with this login. */
    public void setEmployee(Employee emp) {
        this.employee = emp;
    }

    /** @return the employee associated with this login. */
    public Employee getEmployee() {
        return employee;
    }

}
