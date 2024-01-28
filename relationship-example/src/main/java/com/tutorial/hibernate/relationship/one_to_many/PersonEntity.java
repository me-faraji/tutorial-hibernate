package com.tutorial.hibernate.relationship.one_to_many;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_person")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    String email;
    @Column(name = "phone_number", length = 10)
    String phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    Set<OrderEntity> orderSet;
}
