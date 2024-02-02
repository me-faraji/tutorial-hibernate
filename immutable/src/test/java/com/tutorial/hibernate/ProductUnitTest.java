package com.tutorial.hibernate;

import com.tutorial.hibernate.immutable.CompanyEntity;
import com.tutorial.hibernate.immutable.ConnectionUtil;
import com.tutorial.hibernate.immutable.ProductEntity;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProductUnitTest {
    private static long product1 = 111;
    private static long product2 = 112;

    @Test
    public void updateUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            ProductEntity product = session.get(ProductEntity.class, product1, LockMode.NONE);
            System.out.printf(MessageFormat.format("TRD: {0}, product-1: {1}\n", Thread.currentThread().getId(), product.toString()));
            product.setName("product3");
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-product: {1}\n", Thread.currentThread().getId(), product.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }

    @Test
    public void loadUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            ProductEntity product = session.load(ProductEntity.class, product1);
            product.getName();
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }

    @Test
    public void getUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    session.get(ProductEntity.class, product1);
                    trx.commit();
                    trx = session.beginTransaction();
                    session.get(ProductEntity.class, product1);
                    trx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    trx.rollback();
                }
            });

//            es.execute(() -> {
//                Transaction trx = null;
//                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
//                    Thread.sleep(2000);
//                    trx = session.beginTransaction();
//                    session.get(ProductEntity.class, product1);
//                    trx.commit();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    trx.rollback();
//                }
//            });
            es.shutdown();
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
