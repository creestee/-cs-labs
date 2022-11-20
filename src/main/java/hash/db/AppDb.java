package hash.db;

import hash.entity.UserCredentials;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;

public class AppDb {
    public static void main(String[] args) {

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        UserCredentials user2 = new UserCredentials("User2", "test124");
        UserCredentials user1 = new UserCredentials("User", DigestUtils.sha256Hex("test123"));

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user1.setId(0);
            user2.setId(1);
            session.save(user1);
            session.save(user2);
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
