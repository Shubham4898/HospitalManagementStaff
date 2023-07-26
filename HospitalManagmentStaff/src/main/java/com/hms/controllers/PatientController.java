package com.hms.controllers;

import com.hms.dto.AdmissionDto;
import com.hms.dto.AdmitPatientDto;
import com.hms.dto.BillDto;
import com.hms.entities.Admission;
import com.hms.entities.Bill;
import com.hms.entities.Patient;
import com.hms.service.AdmissionService;
import com.hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private AdmissionService admissionService;

    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient){
        return  ResponseEntity.status(HttpStatus.CREATED).body(patientService.add(patient));
    }


    //admit
    @PostMapping("/admit/{patientId}")
    public ResponseEntity<AdmissionDto> admit(@RequestBody AdmitPatientDto admissionDto, @PathVariable long patientId ){
        return ResponseEntity.status(HttpStatus.CREATED).body(admissionService.admit(admissionDto,patientId));
    }


    //discharge
    @PostMapping("/discharge/{id}")
    public ResponseEntity<BillDto> discharge(@PathVariable("id") int patientId){
            return ResponseEntity.ok(admissionService.discharge(patientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Long patientId){
        return ResponseEntity.ok(patientService.getPatient(patientId));
    }

    //allPatient
    @GetMapping("/all")
    ResponseEntity<List<Patient>> getAll(){
         return ResponseEntity.ok(patientService.getAll());
    }



}
