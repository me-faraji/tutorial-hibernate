package com.tutorial.hibernate.Inheritance;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.text.MessageFormat;

@Entity
@Table(name = "profile")
@Getter
@Setter
@Immutable
public final class ProfileEntity {
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

    public ProfileEntity() {}

    public ProfileEntity(String firstName, String lastName, String nationalNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalNumber = nationalNumber;
    }

    public static ProfileEntity newInstance(String firstName, String lastName, String nationalNumber) {
        return new ProfileEntity(firstName, lastName, nationalNumber);
    }

    public String toString() {
        return MessageFormat.format("id: {0}, firstName: {1}, lastName: {2}, nationalNumber: {3}", id, firstName, lastName, nationalNumber);
    }

}
