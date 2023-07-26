package com.hms.dto;

import com.hms.entities.HospitalStaff;
import com.hms.entities.Patient;
import lombok.*;

import javax.print.Doc;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Builder
public class BillDto {

    private int id;
    private Patient patient;
    private UserDetailsDto Doctor;
    private Double amount;
    private String ModeOfPayment;
}
