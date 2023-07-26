package com.hms.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;


    public Role(String name) {
        this.name = name;
    }

    public Role() {

    }
}


