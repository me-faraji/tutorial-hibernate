package com.tutorial.hibernate.Inheritance.mapped.superclass;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeEntity<E, T extends Serializable, U extends Serializable> extends BaseEntity<E, U> {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email")
    private String email;
}
