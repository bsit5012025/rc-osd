package org.rocs.osd.data.dao.login.impl;

import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.rocs.osd.model.login.Login;
import org.rocs.osd.util.HibernateUtil;

public class LoginNativeDaoImpl {

    public Login findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Login.class, id);
        }
    }

    public Login findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Login l WHERE l.username = :user";
            SelectionQuery<Login> query = session.createSelectionQuery(hql, Login.class);
            query.setParameter("user", username);

            return query.uniqueResult();
        }
    }
}