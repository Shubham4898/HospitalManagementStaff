package com.hms.service;


import com.hms.dto.AdmissionDto;
import com.hms.dto.AdmitPatientDto;
import com.hms.dto.BillDto;
import com.hms.entities.Admission;
import com.hms.entities.Bill;

public interface AdmissionService {
    AdmissionDto admit(AdmitPatientDto patientDto, long patientId);
    BillDto discharge(int admissionId);

}
