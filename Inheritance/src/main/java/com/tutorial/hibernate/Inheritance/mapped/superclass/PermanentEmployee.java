package com.tutorial.hibernate.Inheritance.mapped.superclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class PermanentEmployee extends Employee<PermanentEmployee, Long> {
    @Column(name = "start_date", nullable = false)
    private Date endDate;
    @Column(name = "level", nullable = false)
    private String level;
}
