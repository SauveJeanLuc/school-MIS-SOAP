package com.edu.schoolsoapapi.soap.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student {

    @Id
    @GeneratedValue
    private Long Id;
    private String firstName;
    private String lastName;
    private String email;

    @OneToOne
    private Course course;

//    (fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "id")

}
