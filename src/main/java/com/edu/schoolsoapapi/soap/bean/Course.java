package com.edu.schoolsoapapi.soap.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String description;

    @Override
    public String toString() {
        return String.format("Course [id=%s, name=%s, description=%s]", id, name, description);
    }

}
