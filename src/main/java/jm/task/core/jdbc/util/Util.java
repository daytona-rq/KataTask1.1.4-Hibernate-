package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/KataTask";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static SessionFactory sessionFactory = null;

    private Util() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static SessionFactory getSessionFactory() throws SQLException {

        if (sessionFactory == null) {
            Map<String, String> settings = new HashMap<>();
            settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            settings.put("hibernate.connection.url", URL);
            settings.put("hibernate.connection.username", USER);
            settings.put("hibernate.connection.password", PASSWORD);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                    applySettings(settings).build();

            sessionFactory = new MetadataSources(serviceRegistry).
                    addAnnotatedClass(User.class).
                    getMetadataBuilder().
                    build().
                    getSessionFactoryBuilder().
                    build();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
