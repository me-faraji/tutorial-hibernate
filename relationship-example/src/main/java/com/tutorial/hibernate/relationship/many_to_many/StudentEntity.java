package com.tutorial.hibernate.relationship.many_to_many;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name = "first_name")
    String firstName;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_course", joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    Set<CourseEntity> courseList;
}
