package org.rocs.osd.facade.login;



/**
 * Facade interface for managing user login operations in the Office of Student
 * Discipline System.
 */
public interface LoginFacade {

    /**
     * Authenticates a user by checking the username and
     * password against the database.
     *
     * @param inputUserName the username entered by the user.
     * @param inputPassword the password entered by the user.
     * @return true if the username exists and
     * the password matches, false otherwise.
     */
    boolean login(String inputUserName, String inputPassword);
}
