/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.a00n.service;

import com.a00n.dao.IDao;
import com.a00n.entities.Service;
import com.a00n.utils.HibernateUtil;
import com.a00n.utils.PersistenceSessionFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author ay0ub
 */
@Named
@ApplicationScoped
public class ServiceService implements IDao<Service> {

    private Session session;

    public ServiceService() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public boolean create(Service o) {
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean update(Service o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Service o) {
        session.beginTransaction();
        session.remove(o);
        session.getTransaction().commit();
        return true;
    }

    @Override
    public Service getById(Long id) {
        Service service = null;
        session.beginTransaction();
        service = (Service) session.get(Service.class, id);
        session.getTransaction().commit();
        return service;
    }

    @Override
    public List<Service> getAll() {
        List<Service> services = null;
//        session.beginTransaction();
        services = session.createQuery("from Service", Service.class).list();
//        session.getTransaction().commit();
        return services;
    }

}
