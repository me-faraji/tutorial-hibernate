package com.tutorial.hibernate.lock.pessimistic;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "lock_pessimistic_tb_order")
public class OrderEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "order_name")
    String orderName;
    @Column(name = "total_amount")
    Double totalAmount = 0.0;
    @Column(name = "currency_code")
    String currencyCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    PersonEntity person;
}
