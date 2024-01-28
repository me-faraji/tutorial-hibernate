package com.tutorial.hibernate.relationship.many_to_many;

import com.tutorial.hibernate.relationship.ConnectionUtil;
import com.tutorial.hibernate.relationship.one_to_many.PersonEntity;
import com.tutorial.hibernate.relationship.one_to_one.join_table.AddressEntity;
import com.tutorial.hibernate.relationship.one_to_one.join_table.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

public class ManyToManyTest {
    public static void main(String[] args) {
        CourseEntity courseEntity1 = CourseEntity.builder().name("Java").build();
        CourseEntity courseEntity2 = CourseEntity.builder().name("Go").build();

        StudentEntity studentEntity1 = StudentEntity.builder().firstName("mehdi").courseList(new HashSet<>(Arrays.asList(courseEntity1, courseEntity2))).build();
        StudentEntity studentEntity2 = StudentEntity.builder().firstName("aref").courseList(new HashSet<>(Collections.singletonList(courseEntity1))).build();

        courseEntity1.setStudentList(new HashSet<>(Arrays.asList(studentEntity1, studentEntity2)));
        courseEntity2.setStudentList(new HashSet<>(Collections.singletonList(studentEntity1)));

        Serializable student1 = save(studentEntity1);
        Serializable student2 = save(studentEntity2);
//        System.out.printf(MessageFormat.format("user: {0}\n", student1));
//        System.out.printf(MessageFormat.format("user: {0}\n", student2));

        getStudent(student1);
    }
    private static Long save(StudentEntity entity) {
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

    public static void getStudent(Serializable id) {
        Transaction trx = null;
        try(Session session = ConnectionUtil.getSessionFactory().openSession()) {
            trx = session.beginTransaction();
            StudentEntity student = session.get(StudentEntity.class, id);
            System.out.println("student fetched: "+ student.getId());
            trx.commit();
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        }
    }

}
