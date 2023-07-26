package com.hms.dto;

import com.hms.entities.AdmissionType;
import com.hms.entities.Patient;
import com.hms.enums.AdmissionStatus;
import com.hms.service.PatientService;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionDto {

    private Patient patient;
    private long dcotorId;
    private int roomNo;
    private AdmissionType admissionType;
    private Date admissionDate;
    private AdmissionStatus status;
    private Date dischargedDate;
}
