package com.tutorial.hibernate.Inheritance.mapped.superclass;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_permanent")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PermanentEmployeeEntity extends EmployeeEntity<PermanentEmployeeEntity, Long, Long> {
    @Column(name = "start_date", nullable = false)
    private Date endDate;
    @Column(name = "level", nullable = false)
    private String level;
}
