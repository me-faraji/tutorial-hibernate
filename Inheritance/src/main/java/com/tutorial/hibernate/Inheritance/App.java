package com.tutorial.hibernate.Inheritance;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.Field;
import java.text.MessageFormat;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        ProfileEntity profile = new ProfileEntity("mehdi", "faraji", "2972306694");
//        profile.setFirstName("mehdi");
//        profile.setLastName("faraji");
//        profile.setNationalNumber("2972306694");
        Long id  = save(profile);
        System.out.println(MessageFormat.format("id: {0}", id));
        updateReflection(id);
        get(id);
    }

    private static Long save(ProfileEntity profile) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            Long id = (Long) session.save(profile);
            trx.commit();
            return id;
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    private static void update(Long id) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            ProfileEntity profile = session.get(ProfileEntity.class, id);
            System.out.println("before update: "+ profile.toString());
            profile.setFirstName("aref");
            session.update(profile);
            trx.commit();
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void get(Long id) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            ProfileEntity profile = session.get(ProfileEntity.class, id);
            System.out.println("after update: "+ profile.toString());
            trx.commit();
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void updateReflection(Long id) {
        Transaction transaction = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ProfileEntity profile = session.get(ProfileEntity.class, id);
            System.out.println("before update: "+ profile.toString());
            Field firstName = profile.getClass().getDeclaredField("firstName");
            firstName.setAccessible(true);
            firstName.set(profile, "aref");
            session.merge(profile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
