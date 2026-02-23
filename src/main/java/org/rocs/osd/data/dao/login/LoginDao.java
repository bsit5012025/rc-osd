package org.rocs.osd.data.dao.login;

import org.rocs.osd.model.login.Login;

public interface LoginDao {

    Login findById (long id);

    Login findByUsername (String username);
}
