package com.tutorial.hibernate.lock.optimistic;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Version
    @Column(name = "version")
    volatile int version;
}
