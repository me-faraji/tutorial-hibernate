package com.tutorial.hibernate.Inheritance.table.perclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "inheritance_tb_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity<E, T extends Serializable, U> extends BaseEntity<E, U> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;

    @Column(name = "name", nullable = false)
    private String name;
}
