package com.hms.service.impl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.Optional;

import com.hms.dto.AdmissionDto;
import com.hms.dto.AdmitPatientDto;
import com.hms.dto.BillDto;
import com.hms.entities.*;
import com.hms.repositories.*;
import com.hms.service.AdmissionService;
import com.hms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AdmissionServiceImplTest {

    @Mock
    private AdmissionRepo admissionRepo;

    @Mock
    private PatientRepo patientRepo;

    @Mock
    private HospitalStaffRepo hospitalStaffRepo;

    @Mock
    private AdmissionTypeRepo admissionTypeRepo;

    @Mock
    private BilRepo bilRepo;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdmissionServiceImpl admissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdmit() {
        // Create sample input data
        AdmitPatientDto admitPatientDto = new AdmitPatientDto();
        admitPatientDto.setDoctorId(1);
        admitPatientDto.setAdmissionTypeId(2);
        admitPatientDto.setRoomNo(101);

        long patientId = 100L;

        // Mock the required entities
        Patient patient = new Patient();
        HospitalStaff doctor = new HospitalStaff();
        AdmissionType admissionType = new AdmissionType();
        Admission admission = new Admission();

        // Set up the mock repository calls
        when(patientRepo.findById(patientId)).thenReturn(Optional.of(patient));
        when(hospitalStaffRepo.findById(admitPatientDto.getDoctorId())).thenReturn(Optional.of(doctor));
        when(admissionTypeRepo.findById(admitPatientDto.getAdmissionTypeId())).thenReturn(Optional.of(admissionType));
        when(admissionRepo.save(any(Admission.class))).thenReturn(admission);

        // Perform the admit operation
        AdmissionDto admissionDto = admissionService.admit(admitPatientDto, patientId);

        // Verify that the repository save method was called with the correct Admission object
        verify(admissionRepo).save(any(Admission.class));
    }

    @Test
    void testDischarge() {
        // Create sample input data
        int admissionId = 123;

        // Mock the required entities
        Admission admission = new Admission();
        admission.setId(admissionId);
        admission.setAdmissionDate(new Date());
        int admissionTypeId = 1;
        AdmissionType at = new AdmissionType(1,"normal",200,400);
        admission.setAdmissionType(at);


        // Set up the mock repository calls
        when(admissionRepo.findById(admissionId)).thenReturn(Optional.of(admission));
        when(bilRepo.save(any(Bill.class))).thenReturn(new Bill());
        when(admissionTypeRepo.save(any(AdmissionType.class))).thenReturn(new AdmissionType());
        when(admissionTypeRepo.findById(admissionTypeId)).thenReturn(Optional.of(at));

        // Perform the discharge operation
        BillDto billDto = admissionService.discharge(admissionId);

        // Verify that the repository save method was called with the correct Bill object
        verify(bilRepo).save(any(Bill.class));

    }




}

