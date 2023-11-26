package com.a00n.utils;

import com.a00n.entities.Employe;
import com.a00n.entities.Service;
import com.a00n.entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author ay0ub
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory = null;

    static {
        if (sessionFactory == null) {
            final StandardServiceRegistry registry
                    = new StandardServiceRegistryBuilder()
                            .build();
            try {
                sessionFactory = new MetadataSources(registry)
                        .addAnnotatedClass(Service.class)
                        .addAnnotatedClass(Employe.class)
                        .addAnnotatedClass(User.class)
                        .buildMetadata()
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
