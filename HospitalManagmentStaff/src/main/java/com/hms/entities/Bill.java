package com.hms.entities;

import com.hms.service.PatientService;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private HospitalStaff Doctor;
    @Column
    private Double amount;
    @Column
    private String ModeOfPayment;
}
