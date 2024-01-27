package com.tutorial.hibernate.Inheritance.joined;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_book")
@PrimaryKeyJoinColumn(name = "book_id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookEntity extends ProductEntity<BookEntity, Long, Long> {

    @Column(name = "author")
    private String author;
}
