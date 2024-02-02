package com.tutorial.hibernate.Inheritance.table.perclass;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "inheritance_tb_pen")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PenEntity extends ProductEntity<PenEntity, Long, Long> {
    @Column(name = "color")
    private String color;
}
