package com.tutorial.hibernate.relationship.one_to_many;

import com.tutorial.hibernate.relationship.ConnectionUtil;
import com.tutorial.hibernate.relationship.one_to_one.foreign_key.AddressEntity;
import com.tutorial.hibernate.relationship.one_to_one.foreign_key.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OneToManyTest {
    public static void main(String[] args) {
        String currencyCode = "123";
        OrderItemEntity item1 = OrderItemEntity.builder().productName("pen").productCategory("tahrir").price(125.0).currencyCode(currencyCode).build();
        OrderItemEntity item2 = OrderItemEntity.builder().productName("pen2").productCategory("tahrir").price(125.0).currencyCode(currencyCode).build();
        OrderItemEntity item3 = OrderItemEntity.builder().productName("pen3").productCategory("tahrir").price(125.0).currencyCode(currencyCode).build();
        Set<OrderItemEntity> orderItems = new HashSet<>();
        orderItems.add(item1);
        orderItems.add(item2);
        orderItems.add(item3);
        OrderEntity order1 = OrderEntity.builder().orderItems(orderItems).orderName("order1").totalAmount(orderItems.stream().parallel().map(OrderItemEntity::getPrice).reduce(0.0, Double::sum)).currencyCode(currencyCode).build();
        item1.setOrder(order1);
        item2.setOrder(order1);
        item3.setOrder(order1);

        currencyCode = "456";
        OrderItemEntity book1 = OrderItemEntity.builder().productName("book1").productCategory("tahrir").price(125.0).currencyCode(currencyCode).build();
        OrderItemEntity book2 = OrderItemEntity.builder().productName("book2").productCategory("tahrir").price(125.0).currencyCode(currencyCode).build();
        OrderItemEntity book3 = OrderItemEntity.builder().productName("book3").productCategory("tahrir").price(125.0).currencyCode(currencyCode).build();
        Set<OrderItemEntity> books = new HashSet<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        OrderEntity order2 = OrderEntity.builder().orderItems(books).orderName("order2").totalAmount(books.stream().parallel().map(OrderItemEntity::getPrice).reduce(0.0, Double::sum)).currencyCode(currencyCode).build();
        book1.setOrder(order2);
        book2.setOrder(order2);
        book3.setOrder(order2);

        Set<OrderEntity> orders = new HashSet<>();
        orders.add(order1);
        orders.add(order2);

        PersonEntity person = PersonEntity.builder().firstName("mehdi").lastName("faraji").phoneNumber("9379644266").email("me.faraji67@gmail.com").orderSet(orders).build();
        order1.setPerson(person);
        order2.setPerson(person);
        Serializable personId = save(person);
//        System.out.printf(MessageFormat.format("person: {0}\n", personId));
        getPerson(personId);
        List<OrderEntity> orderList = getOrder(personId);
        List<OrderItemEntity> orderItemList = getOrderItem(orderList.get(0).getId());
        System.out.println("");
    }
    private static Long save(PersonEntity entity) {
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

    public static void getPerson(Serializable id) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, id);
            System.out.println("person fetched: "+ person.getId());
            trx.commit();
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        }
    }

    public static List<OrderEntity> getOrder(Serializable id) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            List<OrderEntity> orders = session.createQuery("from OrderEntity where person.id = :id").setParameter("id", id).list();
            System.out.println("order fetched: "+ orders.size());
            trx.commit();
            return orders;
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public static List<OrderItemEntity> getOrderItem(Serializable id) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            List<OrderItemEntity> orderItems = session.createQuery("from OrderItemEntity where order.id = :id").setParameter("id", id).list();
            System.out.println("item fetched: "+ orderItems.size());
            trx.commit();
            return orderItems;
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

}
