package com.tutorial.hibernate.h2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        ProfileEntity profile = new ProfileEntity(1367L, "mehdi", "faraji", "2972306694");
        System.out.println(MessageFormat.format("id: {0}", save(profile)));
    }

    private static Serializable save(ProfileEntity profile) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            Serializable id = session.save(profile);
            trx.commit();
            return id;
//            return null;
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
}
