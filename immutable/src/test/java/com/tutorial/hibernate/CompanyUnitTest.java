package com.tutorial.hibernate;

import com.tutorial.hibernate.immutable.ConnectionUtil;
import com.tutorial.hibernate.immutable.CompanyEntity;
import com.tutorial.hibernate.immutable.ProductEntity;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompanyUnitTest {
    private static long id = 110;
    private static Serializable serializable = 110;
//    @Before
//    public void setUp() {
//        Transaction trx = null;
//        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
//            trx = session.beginTransaction();
//            session.createNativeQuery("delete from immutable_company").executeUpdate();
//            session.createNativeQuery("delete from immutable_product").executeUpdate();
//            trx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            trx.rollback();
//        }
//    }

    @Test
    public void insertUnitTest() {
        CompanyEntity company = CompanyEntity.builder().name("com-1").build();
        ProductEntity product1 = ProductEntity.builder().name("pro-1").company(company).build();
        ProductEntity product2 = ProductEntity.builder().name("pro-2").company(company).build();
//        company.setProducts(new HashSet<>(Arrays.asList(product1, product2)));

        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            System.out.printf(MessageFormat.format("save company: {0}", session.save(company)));
            System.out.printf(MessageFormat.format("save product: {0}", session.save(product1)));
            System.out.printf(MessageFormat.format("save product: {0}", session.save(product2)));
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }

    @Test
    public void addToProductListUnitTest() {
//        ProductEntity product1 = ProductEntity.builder().name("pro-2").company(company).build();
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            CompanyEntity company = session.get(CompanyEntity.class, id);
            System.out.printf(MessageFormat.format("TRD: {0}, first-company: {1}\n", Thread.currentThread().getId(), company.toString()));
//            company.getProducts().add(ProductEntity.builder().name("pro-3").company(company).build());
            trx.commit();
            System.out.printf(MessageFormat.format("TRD: {0}, last-company: {1}\n", Thread.currentThread().getId(), company.toString()));
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }
    @Test
    public void getUnitTest() {
        Transaction trx = null;
        try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            session.get(CompanyEntity.class, id);
            trx.commit();
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
            CompanyEntity company = session.load(CompanyEntity.class, id);
            company.getName();
            trx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trx.rollback();
        }
    }

    @Test
    public void concurrentLoadUnitTest() {
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            es.execute(() -> {
                Transaction trx = null;
                try (Session session = ConnectionUtil.getSessionFactory().openSession()) {
                    trx = session.beginTransaction();
                    CompanyEntity company = session.load(CompanyEntity.class, id);
//                    System.out.printf(MessageFormat.format("load: {0}\n", company.toString()));
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
                    CompanyEntity company = session.load(CompanyEntity.class, id);
                    String name = company.getName();
//                    System.out.printf(MessageFormat.format("get: {0}\n", company.toString()));
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
