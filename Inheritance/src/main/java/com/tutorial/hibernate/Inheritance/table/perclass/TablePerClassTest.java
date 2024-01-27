package com.tutorial.hibernate.Inheritance.table.perclass;

import com.tutorial.hibernate.Inheritance.ConnectionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

public class TablePerClassTest {
    public static void main(String[] args) {
        BookEntity bookEntity = BookEntity.builder().author("mehdi").build();
        bookEntity.setName("java");
        bookEntity.setCreatedBy(1367L);
        bookEntity.setCreatedOn(new Date());
        Serializable ContractId = save(bookEntity);
        System.out.printf(MessageFormat.format("book: {0}\n", ContractId));

        PenEntity penEntity = PenEntity.builder().color("red").build();
        penEntity.setName("bik");
        penEntity.setCreatedBy(1367L);
        penEntity.setCreatedOn(new Date());
        Serializable permanentId = save(penEntity);
        System.out.printf(MessageFormat.format("pen: {0}", permanentId));
        show();
    }

    private static void show() {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            List<?> product =  session.createQuery("from ProductEntity").getResultList();
            System.out.printf(MessageFormat.format("size of products: {0}", product.size()));
            trx.commit();
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        }
    }
    private static Long save(BookEntity entity) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            Long id = (Long) session.save(entity);
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

    private static Long save(PenEntity entity) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            Long id = (Long) session.save(entity);
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
}
