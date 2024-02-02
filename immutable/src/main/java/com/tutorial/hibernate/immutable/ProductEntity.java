package com.tutorial.hibernate.immutable;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Immutable
@Entity(name = "immutable_product")
public class ProductEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
