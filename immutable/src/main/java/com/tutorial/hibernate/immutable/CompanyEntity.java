package com.tutorial.hibernate.immutable;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "immutable_company")
public class CompanyEntity extends BaseEntity {

    private String name;
    @Immutable
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

    public String toString() {
        return MessageFormat.format("id: {0}, name: {1}, size: {3}", id, name, products.size());
    }

}
