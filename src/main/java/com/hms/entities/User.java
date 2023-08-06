package com.hms.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column @NotEmpty(message = "name can't be empty")
    private String name;
    @Column @Min(18)
    private int age;
    @Column
    private String phoneNumber;
    @Column
    private String address;
}
