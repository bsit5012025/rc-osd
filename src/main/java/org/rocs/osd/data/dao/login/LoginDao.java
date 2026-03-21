package org.rocs.osd.data.dao.login;

import org.rocs.osd.model.login.Login;

/**
 * DAO interface for managing Login records in the Office of
 * Student Discipline System.
 * Provides methods for retrieving login credentials
 * and associated user info.
 */
public interface LoginDao {

    /**
     * Finds and retrieves a Login object from the database by username.
     * @param username the username to search for.
     * @return a Login object containing the credentials and associated person
     * information.
     */
    Login findLoginByUsername(String username);
}