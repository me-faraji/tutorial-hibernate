package com.tutorial.hibernate.Inheritance.table.perclass;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookEntity extends ProductEntity<BookEntity, Long, Long> {
    @Column(name = "author")
    private String author;
}
