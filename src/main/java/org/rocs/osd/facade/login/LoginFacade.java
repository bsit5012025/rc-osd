package org.rocs.osd.facade.login;

import org.rocs.osd.model.login.Login;

public interface LoginFacade
{
    boolean login(String inputUserName, String inputPassword);
}
