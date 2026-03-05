package org.rocs.osd.model.login;

/**
 * Represents a user's login credentials in the Office of Student Discipline System.
 * Stores the unique ID, username, and password of a user.
 */
public class Login {

    /**
     * Unique identifier for the login record
      */
    private long id;

    /**
     * Username for login
     */
    private String username;

    /**
     * Password for login
     */
    private String password;

    /**
     * Default constructor.
     */
    public Login() {
    }


    /**
     * @param id unique identifier
     * @param username username of the user
     * @param password password of the user
     */
    public Login(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }



    /**
     * Gets the ID of the login record
     * @return the ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the login record
     * @param id the new ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
