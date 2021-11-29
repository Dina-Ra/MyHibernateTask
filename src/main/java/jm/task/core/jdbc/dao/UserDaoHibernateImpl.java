package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory factory = Util.getConnectionHibernate();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), " +
                    "last_name VARCHAR(255), age INT)").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = factory.openSession();
        Transaction transaction;
        try {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.openSession();
        Transaction transaction;
        try {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Session session = factory.openSession();
        Transaction transaction;
        try {
            transaction = session.beginTransaction();
            list = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            List<User> list = session.createCriteria(User.class).list();
            for (User user : list){
                session.delete(user);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();

        }
    }
}
