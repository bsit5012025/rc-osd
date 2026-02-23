package org.rocs.osd.data.dao.login.impl;

import jakarta.persistence.EntityManager;
import org.rocs.osd.data.dao.login.LoginDao;
import org.rocs.osd.model.login.Login;
import jakarta.persistence.TypedQuery;

public class LoginDaoImpl implements LoginDao  {

    private final EntityManager em;

    public LoginDaoImpl (EntityManager em) {
        this.em = em;
    }

    @Override
    public Login findById(long id) {
        return this.em.find(Login.class, id);
    }

    @Override
    public Login findByUsername(String username) {
        String jpql = "SELECT login FROM Login login WHERE login.username = :username";
        TypedQuery<Login> query = this.em.createQuery(jpql, Login.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }
}
