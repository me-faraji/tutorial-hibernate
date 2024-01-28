package com.tutorial.hibernate.relationship.one_to_many;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_category")
    String productCategory;
    Double price;
    @Column(name = "currency_code")
    String currencyCode;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
