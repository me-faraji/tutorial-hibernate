package com.hibernate.tutorial;

import com.tutorial.hibernate.lock.ConnectionUtil;
import com.tutorial.hibernate.lock.pessimistic.OrderEntity;
import com.tutorial.hibernate.lock.pessimistic.PersonEntity;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.LockModeType;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PessimisticLockPersonEntityUnitTest {

    public static long personId = 98L;

//    @Before
//    public void setUp() {
//        Transaction trx = null;
//        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
//            trx = session.beginTransaction();
//            session.createNativeQuery("delete from lock_pessimistic_tb_order").executeUpdate();
//            session.createNativeQuery("delete from lock_pessimistic_tb_person").executeUpdate();
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
    public void lockPessimisticReadUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_READ);
            System.out.printf(MessageFormat.format("TRD: {0}, person-1: {1}\n", Thread.currentThread().getId(), person.toString()));
            Thread.sleep(20000);
            person.setFirstName("PESSIMISTIC_READ");
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-person: {1}\n", Thread.currentThread().getId(), person.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void concurrentPessimisticReadUpdateUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_READ);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update1\n", Thread.currentThread().getId(), person.getId()));
                    session.refresh(person, LockModeType.PESSIMISTIC_READ);
                    person.setFirstName("PESSIMISTIC_READ1");
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
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_READ);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update2\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_READ2");
                    trx.commit();
                    System.out.printf(MessageFormat.format("update2 successful: {0} ...\n", Thread.currentThread().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });

            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_READ);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update3\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_READ3");
                    trx.commit();
                    System.out.printf(MessageFormat.format("update3 successful: {0} ...\n", Thread.currentThread().getId()));
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
    public void lockPessimisticWriteUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_WRITE);
            System.out.printf(MessageFormat.format("TRD: {0}, first-person: {1}\n", Thread.currentThread().getId(), person.toString()));
            person.setFirstName("PESSIMISTIC_WRITE");
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-person: {1}\n", Thread.currentThread().getId(), person.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void concurrentPessimisticWriteUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_WRITE);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update1\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_WRITE1");
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
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_WRITE);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update2\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_WRITE2");
                    trx.commit();
                    System.out.printf(MessageFormat.format("update2 successful: {0} ...\n", Thread.currentThread().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });

            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_WRITE);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update3\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_WRITE3");
                    trx.commit();
                    System.out.printf(MessageFormat.format("update3 successful: {0} ...\n", Thread.currentThread().getId()));
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
    public void pessimisticForceIncrementUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_FORCE_INCREMENT);
            System.out.printf(MessageFormat.format("TRD: {0}, first-person: {1}\n", Thread.currentThread().getId(), person.toString()));
            person.setFirstName("PESSIMISTIC_FORCE_INCREMENT");
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-person: {1}\n", Thread.currentThread().getId(), person.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void concurrentPessimisticForceIncrementWriteUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(3);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_FORCE_INCREMENT);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update1\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_FORCE_INCREMENT1");
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
//                    Thread.sleep(1000);
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_FORCE_INCREMENT);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update2\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_FORCE_INCREMENT2");
                    trx.commit();
                    System.out.printf(MessageFormat.format("update2 successful: {0} ...\n", Thread.currentThread().getId()));
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });

            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
//                    Thread.sleep(2000);
                    trx = session.beginTransaction();
                    PersonEntity person = session.get(PersonEntity.class, personId, LockMode.PESSIMISTIC_WRITE);
                    System.out.printf(MessageFormat.format("TRD: {0}, get person: {1}, update3\n", Thread.currentThread().getId(), person.getId()));
                    person.setFirstName("PESSIMISTIC_FORCE_INCREMENT3");
                    trx.commit();
                    System.out.printf(MessageFormat.format("update3 successful: {0} ...\n", Thread.currentThread().getId()));
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
