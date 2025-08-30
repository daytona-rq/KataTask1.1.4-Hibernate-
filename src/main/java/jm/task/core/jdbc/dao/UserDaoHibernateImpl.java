package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            String query = "CREATE TABLE IF NOT EXISTS USERS (" +
                    "id SERIAL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "lastName VARCHAR(255) NOT NULL, " +
                    "age INTEGER NOT NULL)";

            Transaction tx = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            tx.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            String query = "Drop table users";

            Transaction tx = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            tx.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction tx = session.beginTransaction();
            User newUser = new User(name, lastName, age);
            session.persist(newUser);
            tx.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction tx = session.beginTransaction();
            session.remove(session.get(User.class, id));
            tx.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            Query<User> query = session.createQuery(cq);
            return query.getResultList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void cleanUsersTable() {
        String hql = "TRUNCATE TABLE users";

        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction tx = session.beginTransaction();
            session.createNativeQuery(hql).executeUpdate();
            tx.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
