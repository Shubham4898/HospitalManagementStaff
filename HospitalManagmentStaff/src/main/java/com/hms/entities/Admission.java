package com.hms.entities;

import com.hms.enums.AdmissionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Patient patient;

    @ManyToOne
    private HospitalStaff doctor;

    @Column
    private int roomNo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admission_type" , nullable = false)
    private AdmissionType admissionType;

    @Column
    private Date admissionDate;

    @Column
    private AdmissionStatus status;

    @Column
    private Date dischargedDate;


}
