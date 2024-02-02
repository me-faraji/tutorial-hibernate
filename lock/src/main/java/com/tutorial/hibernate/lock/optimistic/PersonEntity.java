package com.tutorial.hibernate.lock.optimistic;

import lombok.*;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "lock_optimistic_tb_person")
public class PersonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    String email;
    @Column(name = "phone_number", length = 11)
    String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    Set<OrderEntity> orderSet;

    @Override
    public String toString() {
        return MessageFormat.format("id: {0}, firstName: {1}, lastName: {2}, version: {3}",
                id, firstName, lastName, version);
    }
}
