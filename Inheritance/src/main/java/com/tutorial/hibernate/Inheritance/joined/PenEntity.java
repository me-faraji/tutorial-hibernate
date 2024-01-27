package com.tutorial.hibernate.Inheritance.joined;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_pen")
@PrimaryKeyJoinColumn(name = "pen_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PenEntity extends ProductEntity<PenEntity, Long, Long> {

    @Column(name = "color")
    private String color;
}
