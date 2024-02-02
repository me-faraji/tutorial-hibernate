package com.hibernate.tutorial;

import com.tutorial.hibernate.lock.ConnectionUtil;
import com.tutorial.hibernate.lock.optimistic.OrderEntity;
import com.tutorial.hibernate.lock.optimistic.PersonEntity;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OptimisticLockPersonEntityUnitTest {

    public static long personId = 65L;

//    @Before
//    public void setUp() {
//        Transaction trx = null;
//        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
//            trx = session.beginTransaction();
//            session.createNativeQuery("delete from lock_optimistic_tb_order").executeUpdate();
//            session.createNativeQuery("delete from lock_optimistic_tb_person").executeUpdate();
//            trx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            trx.rollback();
//        }
//    }

    @Test
    public void savePersonUnitTest() {
        PersonEntity person = PersonEntity.builder().firstName("mehdi").lastName("faraji").phoneNumber("09379644267")
                .email("me.faraji67.gmail.com").build();
        OrderEntity order1 = OrderEntity.builder().orderName("order1").currencyCode("123").totalAmount(123.0)
                .person(person).build();
        OrderEntity order2 = OrderEntity.builder().orderName("order2").currencyCode("456").totalAmount(456.0)
                .person(person).build();
        person.setOrderSet(new HashSet<>(Arrays.asList(order1, order2)));

        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            System.out.printf(MessageFormat.format("save person: {0}", session.save(person)));
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }

    @Test
    public void concurrentVersionUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update1\n", Thread.currentThread().getId(), person.getId()));
                    Thread.sleep(5000);
                    session.refresh(person, LockMode.READ);
                    person.setFirstName("aref");
                    session.save(person);
                    trx.commit();
                    System.out.printf(MessageFormat.format("update1 successful: {0} ...\n", Thread.currentThread().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });

            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update2\n", Thread.currentThread().getId(), person.getId()));
                    Thread.sleep(1000);
                    person.setFirstName("mehdi");
                    session.save(person);
                    trx.commit();
                    System.out.printf(MessageFormat.format("update2 successful: {0} ...\n", Thread.currentThread().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });
            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lockNoneUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, personId, LockMode.NONE);
            System.out.printf(MessageFormat.format("TRD: {0}, person-1: {1}\n", Thread.currentThread().getId(), person.toString()));
            person.setFirstName("NONE");
//            PersonEntity person1 = session.get(PersonEntity.class, personId, LockMode.NONE);
//            System.out.printf(MessageFormat.format("TRD: {0}, person-2: {1}\n", Thread.currentThread().getId(), person1.toString()));
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-person: {1}\n", Thread.currentThread().getId(), person.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void lockOptimisticUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, personId, LockMode.OPTIMISTIC);
            System.out.printf(MessageFormat.format("TRD: {0}, first-person: {1}\n", Thread.currentThread().getId(), person.toString()));
            person.setFirstName("OPTIMISTIC");
//            session.save(person);
//            PersonEntity person1 = session.get(PersonEntity.class, personId, LockMode.OPTIMISTIC);
//            System.out.printf(MessageFormat.format("TRD: {0}, second-person: {1}\n", Thread.currentThread().getId(), person1.toString()));
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-person: {1}\n", Thread.currentThread().getId(), person.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void lockReadUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, personId, LockMode.READ);
            System.out.printf(MessageFormat.format("TRD: {0}, first-person: {1}\n", Thread.currentThread().getId(), person.toString()));
            person.setFirstName("READ");
//            session.save(person);
//            PersonEntity person1 = session.get(PersonEntity.class, personId, LockMode.READ);
//            System.out.printf(MessageFormat.format("TRD: {0}, second-person: {1}\n", Thread.currentThread().getId(), person1.toString()));
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-person: {1}\n", Thread.currentThread().getId(), person.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void lockOptimisticForceIncrementUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, personId, LockMode.OPTIMISTIC_FORCE_INCREMENT);
            System.out.printf(MessageFormat.format("TRD: {0}, first-person: {1}\n", Thread.currentThread().getId(), person.toString()));
            person.setFirstName("OPTIMISTIC_FORCE_INCREMENT");
//            session.save(person);
//            PersonEntity person1 = session.get(PersonEntity.class, personId, LockMode.OPTIMISTIC_FORCE_INCREMENT);
//            System.out.printf(MessageFormat.format("TRD: {0}, second-person: {1}\n", Thread.currentThread().getId(), person1.toString()));
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-person: {1}\n", Thread.currentThread().getId(), person.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void concurrentOptimisticForceIncrementUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.OPTIMISTIC_FORCE_INCREMENT);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update1\n", Thread.currentThread().getId(), person.getId()));
                    Thread.sleep(5000);
                    person.setFirstName("OPTIMISTIC_FORCE_INCREMENT1");
//                    session.save(person);
                    trx.commit();
                    System.out.printf(MessageFormat.format("update1 successful: {0} ...\n", Thread.currentThread().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });

            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.OPTIMISTIC_FORCE_INCREMENT);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update2\n", Thread.currentThread().getId(), person.getId()));
                    Thread.sleep(1000);
                    person.setFirstName("OPTIMISTIC_FORCE_INCREMENT2");
//                    session.save(person);
                    trx.commit();
                    System.out.printf(MessageFormat.format("update2 successful: {0} ...\n", Thread.currentThread().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });
            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
