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
@Table(name = "tb_course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name = "name")
    String name;
    @ManyToMany(mappedBy = "courseList")
    Set<StudentEntity> studentList;
}
