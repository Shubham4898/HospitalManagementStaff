package com.hms.dto;

import com.hms.entities.AdmissionType;
import lombok.Data;

import java.util.Date;

@Data
public class AdmitPatientDto {

    private long doctorId;
    private int roomNo;
    private int admissionTypeId;
    private Date admissionDate;
}
