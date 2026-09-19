package org.rocs.osd.facade.login.impl;

import org.mindrot.jbcrypt.BCrypt;
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

        if (!"prefect".equals(inputUserName)) {
            return false;
        }

        Login login = loginDao.findLoginByUsername(inputUserName);

        if (login == null || login.getPassword() == null
                || login.getPassword().isBlank()) {
            return false;
        }

        String storedHash = login.getPassword();

        if (storedHash.startsWith("$2b$") || storedHash.startsWith("$2y$")) {
            storedHash = "$2a$" + storedHash.substring(4);
        }

        try {
            return BCrypt.checkpw(inputPassword, storedHash);
        } catch (IllegalArgumentException e) {
            return false;
        }
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

    /**
     * Changes the user's password after validating the new password
     * and verifying the entered OTP.
     * The method returns false if the new password is blank
     * or if the entered OTP does not match the generated OTP.
     * If both validations are successful, the new password is passed
     * to the login DAO for updating.
     *
     * @param changePassword the new password to be set
     * @param generatedOtp the OTP generated for password verification
     * @param enteredOtp the OTP entered by the user
     * @return true if the password and OTP are valid and the
     *         password change is processed. false otherwise
     */
    @Override
    public boolean changePassword(String changePassword,
                                  String generatedOtp,
                                  String enteredOtp
    ) {
        if (changePassword.isBlank()
                || generatedOtp.isBlank()
                || enteredOtp.isBlank()
        ) {
            return false;
        }

        if (!generatedOtp.equals(enteredOtp)) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(
                changePassword,
                BCrypt.gensalt(12)
        );

        boolean queryStatus = loginDao.changePassword(hashedPassword);
        if (!queryStatus) {
            return false;
        }

        return true;
    }
}
