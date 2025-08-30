package jm.task.core.jdbc.util;


import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.Properties;

public class Util {

    static private final String URL = "jdbc:mysql://localhost:3306/KataTask";
    static private final String USER = "root";
    static private final String PASSWORD = "";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static SessionFactory getSessionFactory() throws SQLException {
        Configuration config = new Configuration();

        config.configure("").
                setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver").
                setProperty("hibernate.connection.url", URL).
                setProperty("hibernate.connection.username", USER).
                setProperty("hibernate.connection.password", PASSWORD).
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return config.buildSessionFactory();
    }
}
