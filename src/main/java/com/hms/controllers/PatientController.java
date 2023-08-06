package com.hms.controllers;

import com.hms.dto.AdmissionDto;
import com.hms.dto.AdmitPatientDto;
import com.hms.dto.BillDto;
import com.hms.entities.Admission;
import com.hms.entities.Bill;
import com.hms.entities.Patient;
import com.hms.service.AdmissionService;
import com.hms.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private AdmissionService admissionService;

    @Operation(
            description = " Add Patient controller",
            summary = "Post request  to add Patient data"
    )
    @PostMapping("/add")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient){
        return  ResponseEntity.status(HttpStatus.CREATED).body(patientService.add(patient));
    }


    @Operation(
            description = " Addmit Patient controller",
            summary = "Post request  to admit Patient using patient id"
    )
    @PostMapping("/admit/{patientId}")
    public ResponseEntity<AdmissionDto> admit(@Valid @RequestBody AdmitPatientDto admissionDto, @PathVariable long patientId ){
        return ResponseEntity.status(HttpStatus.CREATED).body(admissionService.admit(admissionDto,patientId));
    }


    @Operation(
            description = "Discharge Patient controller",
            summary = "Post request to discharge patient and return bill response"
    )
    @PostMapping("/discharge/{id}")
    public ResponseEntity<BillDto> discharge(@PathVariable("id") int patientId){
            return ResponseEntity.ok(admissionService.discharge(patientId));
    }

    @Operation(
            description = " Get Patient controller",
            summary = "Get request to fetch patient data using patient id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable("id") Long patientId){
        return ResponseEntity.ok(patientService.getPatient(patientId));
    }

    @Operation(
            description = "Get all  Patient controller",
            summary = "Get request to fetch all patients"
    )
    @GetMapping("/all")
    ResponseEntity<List<Patient>> getAll(){
         return ResponseEntity.ok(patientService.getAll());
    }



}
