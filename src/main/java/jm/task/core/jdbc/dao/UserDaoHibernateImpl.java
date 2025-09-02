package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        try {
            sessionFactory = Util.getSessionFactory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            String query = "CREATE TABLE IF NOT EXISTS USERS (" +
                    "id SERIAL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "age INTEGER NOT NULL)";
            session.createNativeQuery(query).executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            String query = "DROP table IF EXISTS users";
            session.createNativeQuery(query).executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            User newUser = new User(name, lastName, age);
            session.persist(newUser);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> userList = Collections.emptyList();
        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            userList = session.
                    createQuery(cq.select(cq.from(User.class))).
                    getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            String hql = "TRUNCATE TABLE users";
            session.createNativeQuery(hql).executeUpdate();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
