package com.a00n.utils;

import com.a00n.entities.Employe;
import com.a00n.entities.Service;
import jakarta.enterprise.inject.Produces;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author ay0ub
 */
public class SessionFactoryProducer {

    @Produces
    @PersistenceSessionFactory
    public SessionFactory build() {
        System.out.println("hello from build----------------------------------------------------------");
        final StandardServiceRegistry registry
                = new StandardServiceRegistryBuilder()
                        .build();
        try {
            return new MetadataSources(registry)
                    .addAnnotatedClass(Employe.class)
                    .addAnnotatedClass(Service.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
            return null;
        }
    }

}
