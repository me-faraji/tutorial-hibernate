package com.tutorial.hibernate.relationship.one_to_one.shared_primary_kay;

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
    @Column(name = "user_id")
    private long userId;

    @Column(name = "address_line")
    private String addressLine;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
