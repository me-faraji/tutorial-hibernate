package com.hibernate.tutorial;

import com.tutorial.hibernate.lock.ConnectionUtil;
import com.tutorial.hibernate.lock.optimistic.OrderEntity;
import com.tutorial.hibernate.lock.optimistic.PersonEntity;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OptimisticLockOrderEntityUnitTest {

    public static long orderId1 = 51L;
    public static long orderId2 = 52L;
    @Test
    public void withOptimisticUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    OrderEntity order = session.get(OrderEntity.class, orderId1);
                    session.lock(order, LockMode.READ);
                    System.out.printf(MessageFormat.format("TRD: {0}, get order: {1}, update1\n", Thread.currentThread().getId(), order.getId()));
                    Thread.sleep(10000);
                    order.setOrderName("order1".equals(order.getOrderName()) ? "order3" : "order1");
                    session.save(order);
                    trx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });

            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    Thread.sleep(2000);
                    trx = session.beginTransaction();
                    OrderEntity order = session.get(OrderEntity.class, orderId2);
                    session.lock(order, LockMode.READ);
                    System.out.printf(MessageFormat.format("TRD: {0}, get order: {1}, update2\n", Thread.currentThread().getId(), order.getId()));
                    order.setOrderName("order2".equals(order.getOrderName()) ? "order4" : "order2");
                    session.save(order);
                    trx.commit();
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
