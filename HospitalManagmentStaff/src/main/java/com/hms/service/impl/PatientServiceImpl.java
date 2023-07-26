package com.hms.service.impl;

import com.hms.exceptions.ResourceNotFoundException;
import com.hms.entities.Patient;
import com.hms.enums.AdmissionStatus;
import com.hms.repositories.PatientRepo;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepo repo;
    @Override
    public List<Patient> getAll() {
        return  repo.findAll();
    }

    @Override
    public Patient add(Patient patient) {
        return  repo.save(patient);
    }



    @Override
    public Patient getPatient(Long patientId) {
        return repo.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("No Patient with this PatientId:-" + patientId));
    }
}
