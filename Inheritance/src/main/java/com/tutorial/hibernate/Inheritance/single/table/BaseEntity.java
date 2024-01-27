package com.tutorial.hibernate.Inheritance.single.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity<E, U extends Serializable, T extends Serializable> {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private T id;

    @Column(name = "created_by", nullable = false)
    private U createdBy;
    @Column(name = "created_on", nullable = false)
    private Date createdOn;
    @Column(name = "updated_by")
    private U updatedBy;
    @Column(name = "updated_on")
    private Date updatedOn;
}
