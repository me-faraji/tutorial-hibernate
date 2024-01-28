package com.tutorial.hibernate.relationship.one_to_one.join_table;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_address")
public class AddressEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(name = "address_line")
    private String addressLine;

    @OneToOne(mappedBy = "address")
    private UserEntity user;
}
