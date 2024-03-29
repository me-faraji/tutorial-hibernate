package com.tutorial.hibernate.Inheritance.mapped.superclass;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity<E, U> {
    @Column(name = "created_by", nullable = false)
    private U createdBy;
    @Column(name = "created_on", nullable = false)
    private Date createdOn;
    @Column(name = "updated_by")
    private U updatedBy;
    @Column(name = "updated_on")
    private Date updatedOn;
}
