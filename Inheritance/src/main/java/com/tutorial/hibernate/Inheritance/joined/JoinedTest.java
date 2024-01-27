package com.tutorial.hibernate.Inheritance.joined;

import com.tutorial.hibernate.Inheritance.ConnectionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

public class JoinedTest {
    public static void main(String[] args) {
        BookEntity bookEntity = BookEntity.builder().author("mehdi").build();
        bookEntity.setName("java");
        bookEntity.setCreatedBy(1367L);
        bookEntity.setCreatedOn(new Date());
        Serializable bookId = save(bookEntity);
        System.out.printf(MessageFormat.format("book: {0}\n", bookId));

        PenEntity penEntity = PenEntity.builder().color("red").build();
        penEntity.setName("bik");
        penEntity.setCreatedBy(1367L);
        penEntity.setCreatedOn(new Date());
        Serializable penId = save(penEntity);
        System.out.printf(MessageFormat.format("pen: {0}\n", penId));
        show(bookId);
    }

    private static void show(Serializable id) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            BookEntity product =  session.get(BookEntity.class, id);
            System.out.printf(MessageFormat.format("size of products: {0}", product));
//            BookEntity product1 =  session.load(BookEntity.class, id);
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
