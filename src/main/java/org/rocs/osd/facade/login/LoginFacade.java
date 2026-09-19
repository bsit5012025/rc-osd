package org.rocs.osd.facade.login;


import org.rocs.osd.model.login.Login;

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
    /**
     * Retrieves a login record by its username.
     *
     * @param username the username to search for
     * @return the Login object if found, or null if not found
     */
    Login getByUsername(String username);

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
    boolean changePassword(
            String changePassword,
            String generatedOtp,
            String enteredOtp
    );
}
