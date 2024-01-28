package com.tutorial.hibernate.relationship.one_to_one.join_table;

import com.tutorial.hibernate.relationship.ConnectionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.text.MessageFormat;

public class JoinTableTest {
    public static void main(String[] args) {
        AddressEntity address = AddressEntity.builder().addressLine("tehran").build();
        UserEntity user = UserEntity.builder().name("mehdi").address(address).build();
        Serializable ContractId = save(user);
        System.out.printf(MessageFormat.format("user: {0}\n", ContractId));
    }
    private static Long save(UserEntity entity) {
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
