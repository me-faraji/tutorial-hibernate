package com.tutorial.hibernate.lock.optimistic;

import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "lock_optimistic_tb_order")
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
