package com.tutorial.hibernate.h2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.text.MessageFormat;

@Entity
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "national_number")
    private String nationalNumber;

    public String toString() {
        return MessageFormat.format("id: {0}, firstName: {1}, lastName: {2}, nationalNumber: {3}", id, firstName, lastName, nationalNumber);
    }

}
