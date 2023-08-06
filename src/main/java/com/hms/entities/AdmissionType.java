package com.hms.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class AdmissionType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String type;
    @Column
    private double fixedCharge;
    @Column
    private double perDayCharge;

}
