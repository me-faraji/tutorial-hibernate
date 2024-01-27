package com.tutorial.hibernate.Inheritance.mapped.superclass;

import javax.persistence.*;

@MappedSuperclass
public class Employee<E, T> extends BaseEntity<E, T> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email")
    private String email;
}
