package com.tutorial.hibernate.Inheritance.mapped.superclass;

import com.tutorial.hibernate.Inheritance.ConnectionUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

public class MappedSuperclassTest {
    public static void main(String[] args) {
        ContractEmployeeEntity contract = ContractEmployeeEntity.builder().startDate(new Date()).endDate(new Date()).job("programmer").build();
        contract.setFirstName("mehdi");
        contract.setLastName("faraji");
        contract.setCreatedBy(1367L);
        contract.setCreatedOn(new Date());
        Serializable ContractId = save(contract);
        System.out.printf(MessageFormat.format("ContractId: {0}\n", ContractId));

        PermanentEmployeeEntity permanent = PermanentEmployeeEntity.builder().endDate(new Date()).level("Admin").build();
        permanent.setFirstName("aref");
        permanent.setLastName("faraji");
        permanent.setCreatedBy(1367L);
        permanent.setCreatedOn(new Date());
        Serializable permanentId = save(permanent);
        System.out.printf(MessageFormat.format("permanentId: {0}", permanentId));
    }
    private static Long save(ContractEmployeeEntity employee) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            Long id = (Long) session.save(employee);
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

    private static Long save(PermanentEmployeeEntity employee) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            Long id = (Long) session.save(employee);
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
