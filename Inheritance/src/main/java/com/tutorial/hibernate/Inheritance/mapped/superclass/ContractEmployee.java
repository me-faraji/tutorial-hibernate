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
@Table(name = "tb_contract")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractEmployee extends Employee<ContractEmployee, Long> {
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "job", nullable = false)
    private String job;
}
