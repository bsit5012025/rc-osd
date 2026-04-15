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
     * @param pLoginDao the DAO used to access login information
     */
    public LoginFacadeImpl(LoginDao pLoginDao) {
        this.loginDao = pLoginDao;
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
        if (inputUserName == null || inputPassword == null
                || inputUserName.isBlank() || inputPassword.isBlank()) {
            return false;
        }

        Login login = loginDao.findLoginByUsername(inputUserName);

        return login != null && inputPassword.equals(login.getPassword());
    }
    /**
     * Retrieves a Login object by username.
     *
     * If the provided username is null or blank, this method returns null.
     * Otherwise, it delegates the lookup to the LoginDao.
     *
     * @param username the username to search for
     * @return the Login object associated with the given username,
     *         or null if the username is null, blank, or not found
     */
    @Override
    public Login getByUsername(String username) {
        if (username == null || username.isBlank()) {
            return null;
        }
        return loginDao.findLoginByUsername(username);
    }
}
