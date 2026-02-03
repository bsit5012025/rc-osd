package org.rocs.osd.facade.login.impl;

import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.facade.login.LoginFacade;
import org.rocs.osd.model.login.Login;

public class LoginFacadeImpl implements LoginFacade
{
    private LoginDao loginDao;

    public LoginFacadeImpl (LoginDao loginDao)
    {
        this.loginDao = loginDao;
    }

    @Override
    public boolean login(String inputUserName, String inputPassword)
    {
        Login login = loginDao.findLoginByUsername(inputUserName);

        if(login == null)
        {
            return false;
        }

        return inputPassword.equals(login.getPassword());
    }

    @Override
    public Login returnUserInfo(String inputedUserName)
    {
        return loginDao.findLoginByUsername(inputedUserName);
    }
}
