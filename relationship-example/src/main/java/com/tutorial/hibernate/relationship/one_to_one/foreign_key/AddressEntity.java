package com.tutorial.hibernate.relationship.one_to_one.foreign_key;

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
    private long id;

    @Column(name = "address_line")
    private String addressLine;

    @OneToOne(mappedBy = "address")
    private UserEntity userEntity;
}
