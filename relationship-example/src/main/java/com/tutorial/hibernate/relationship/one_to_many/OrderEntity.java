package com.tutorial.hibernate.relationship.one_to_many;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "order_name")
    String orderName;
    @Column(name = "total_amount")
    Double totalAmount = 0.0;
    @Column(name = "currency_code")
    String currencyCode;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    Set<OrderItemEntity> orderItems;
    @ManyToOne
    @JoinColumn(name = "person_id")
    PersonEntity person;
}
