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
@DiscriminatorValue(value = "1")
public class BookEntity extends ProductEntity<BookEntity, Long, Long> {
    @Column(name = "author")
    private String author;
}
