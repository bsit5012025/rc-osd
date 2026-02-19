package org.rocs.osd.facade.login.impl;

import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.model.login.Login;

// This class is used to implement the LoginFacade interface to handle login logic
public class LoginFacadeImpl implements LoginFacade
{
    // This is used to access login data from the database
    private LoginDao loginDao;

    // Constructor to set the login DAO
    public LoginFacadeImpl (LoginDao loginDao)
    {
        this.loginDao = loginDao;
    }

    @Override
    public boolean login(String inputUserName, String inputPassword)
    {
        // This will get the login details from the DB using the username
        Login login = loginDao.findLoginByUsername(inputUserName);

        //Return false if the username does not exist
        if(login == null)
        {
            return false;
        }

        // This is used to check if the entered password matches the stored password
        return inputPassword.equals(login.getPassword());
    }
}
