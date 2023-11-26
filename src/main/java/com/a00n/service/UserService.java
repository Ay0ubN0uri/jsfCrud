/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.service;

import com.a00n.dao.IDao;
import com.a00n.entities.User;
import com.a00n.utils.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author ay0ub
 */
@Named
@ApplicationScoped
public class UserService implements IDao<User> {

    private Session session;

    public UserService() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public boolean create(User o) {
        session.beginTransaction();
        session.persist(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(User o) {
        session.beginTransaction();
        session.merge(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(User o) {
        session.beginTransaction();
        session.remove(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public User getById(Long id) {
        User user = null;
        session.beginTransaction();
        user = (User) session.get(User.class, id);
        session.getTransaction().commit();
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;
        users = session.createQuery("from User", User.class).list();
        return users;
    }

    public User userExist(String username, String password) {
        try {
            var user = session.createQuery("from User u where u.username=:username and u.password=:password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password).getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }
}
