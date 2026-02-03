package org.rocs.osd.data.dao.login;

import org.rocs.osd.model.login.Login;

public interface LoginDao {

    public Login findLoginByUsername(String username);

}
