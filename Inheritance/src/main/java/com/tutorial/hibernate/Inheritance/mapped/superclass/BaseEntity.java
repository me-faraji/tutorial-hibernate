package com.tutorial.hibernate.Inheritance.mapped.superclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseEntity<E, T> {

    @Column(name = "created_by", nullable = false)
    private T createdBy;
    @Column(name = "created_on", nullable = false)
    private Date createdOn;
    @Column(name = "updated_by", nullable = false)
    private T updatedBy;
    @Column(name = "updated_on", nullable = false)
    private Date updatedOn;
}
