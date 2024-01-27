package com.tutorial.hibernate.Inheritance.single.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
@Entity
@Table(name = "tb_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity<E, T extends Serializable, U extends Serializable> extends BaseEntity<E, T, U> {

    @Column(name = "name", nullable = false)
    private String name;
}
