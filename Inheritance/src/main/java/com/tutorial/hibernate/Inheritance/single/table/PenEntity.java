package com.tutorial.hibernate.Inheritance.single.table;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@DiscriminatorValue(value = "2")
public class PenEntity extends ProductEntity<PenEntity, Long, Long> {
    @Column(name = "color")
    private String color;
}
