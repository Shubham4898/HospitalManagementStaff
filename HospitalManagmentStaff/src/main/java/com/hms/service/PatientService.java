package com.hms.service;

import com.hms.dto.AdmissionDto;
import com.hms.entities.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getAll();
    Patient add(Patient patient);

    Patient getPatient(Long patientId);

}
