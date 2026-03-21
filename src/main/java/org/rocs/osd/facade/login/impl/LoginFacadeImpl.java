package org.rocs.osd.facade.login.impl;

import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.model.login.Login;

/**
 * Facade implementation for managing Login operations in the Office of Student
 * Discipline System.
 */
public class LoginFacadeImpl implements LoginFacade {

    /**
     * DAO used to access login data from the database.
     */
    private final LoginDao loginDao;

    /**
     * Constructor to set the login DAO dependency.
     *
     * @param loginDao the DAO used to access login information
     */
    public LoginFacadeImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    /**
     * Authenticates a user using the provided username and password.
     *
     * @param inputUserName the username entered by the user
     * @param inputPassword the password entered by the user
     * @return true if authentication is successful, false otherwise
     */
    @Override
    public boolean login(String inputUserName, String inputPassword) {
        // Retrieve the login details from the database using the username
        Login login = loginDao.findLoginByUsername(inputUserName);

        // Return false if the username does not exist
        if (login == null) {
            return false;
        }

        // Check if the entered password matches the stored password
        return inputPassword.equals(login.getPassword());
    }
}