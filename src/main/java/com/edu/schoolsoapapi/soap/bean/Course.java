package com.edu.schoolsoapapi.soap.bean;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
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
