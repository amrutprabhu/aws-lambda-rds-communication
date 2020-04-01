package com.amrut.prabhu;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseUtil {

    public static SessionFactory createSessionFactory() {

        Configuration configuration = new Configuration();
        String jdbcUrl = "jdbc:mysql://"
                + System.getenv("RDS_HOSTNAME")
                + "/"
                + System.getenv("RDS_DB_NAME");

        configuration.setProperty("hibernate.connection.url", jdbcUrl);
        configuration.setProperty("hibernate.connection.username", System.getenv("RDS_USERNAME"));
        configuration.setProperty("hibernate.connection.password", System.getenv("RDS_PASSWORD"));
        configuration.addAnnotatedClass(User.class);
        configuration.configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        try {
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("Session factory created");
            return sessionFactory;
        } catch (HibernateException e) {
            System.err.println("Session Factory creation failed." + e);
            throw e;
        }

    }
}