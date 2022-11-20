package hash.db;

import hash.entity.UserCredentials;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;

public class AppDb {
    public AppDb() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public void SaveIntoDb(UserCredentials user) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<UserCredentials> users = session.createQuery("from UserCredentials ", UserCredentials.class).list();
            users.forEach(s -> System.out.println(s.getUsername() + " " + "hashed password : " + s.getPassword()));
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
