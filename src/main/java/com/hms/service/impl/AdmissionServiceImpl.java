package com.hms.service.impl;

import com.hms.dto.AdmissionDto;
import com.hms.dto.AdmitPatientDto;
import com.hms.dto.BillDto;
import com.hms.entities.*;
import com.hms.enums.AdmissionStatus;
import com.hms.exceptions.ResourceNotFoundException;
import com.hms.repositories.*;
import com.hms.service.AdmissionService;
import com.hms.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    @Autowired
    private AdmissionRepo admissionRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private HospitalStaffRepo hospitalStaffRepo;
    @Autowired
    private AdmissionTypeRepo admissionTypeRepo;
    @Autowired
    private BilRepo bilRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;


    @Override
    public AdmissionDto admit(AdmitPatientDto admitPatientDto, long patientId) {

        Patient patient =  patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("No Patient Found with id:-" + patientId)
        );

        HospitalStaff doctor = hospitalStaffRepo.findById(admitPatientDto.getDoctorId()).orElseThrow(
                () -> new ResourceNotFoundException("No Doctor found with id:-" + admitPatientDto.getDoctorId())

        );

        AdmissionType admissionType =admissionTypeRepo.findById(admitPatientDto.getAdmissionTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("No AdmissionType with  id:-" + admitPatientDto.getAdmissionTypeId())
        );

        Admission admission=  admissionRepo.save(Admission.builder().admissionDate(new Date()).admissionType(admissionType)
                .doctor(doctor).status(AdmissionStatus.ADMITTED).patient(patient)
                .roomNo(admitPatientDto.getRoomNo()).build());

        return  AdmissionDto.builder().dcotorId(doctor.getId()).admissionDate(admission.getAdmissionDate())
                .admissionType(admission.getAdmissionType()).status(admission.getStatus())
                .roomNo(admission.getRoomNo()).patient(admission.getPatient()).build();


    }

    @Override
    public BillDto discharge(int admissionId) {
        Admission admission = admissionRepo.findById(admissionId).orElseThrow(
                () -> new ResourceNotFoundException("No admission for this id:-" + admissionId)
        );
        admission.setDischargedDate(new Date());
        admission.setStatus(AdmissionStatus.DISCHARGED);
        Bill bill = calculateBill(admission);
        Bill finalBill =  bilRepo.save(bill);
        return BillDto.builder().amount(bill.getAmount()).id(bill.getId())
                .Doctor(userService.convertUsertoDto(bill.getDoctor()))
                .patient(bill.getPatient()).ModeOfPayment(bill.getModeOfPayment()).build();


    }

    private Bill calculateBill(Admission admission){

        AdmissionType  type = admission.getAdmissionType();
        Date date = new Date();
        int noOfAdmitDate = calcualteDayDiff(admission.getDischargedDate(),admission.getAdmissionDate());
        Double amount = type.getFixedCharge() + type.getPerDayCharge()*noOfAdmitDate;
        return  Bill.builder().amount(amount).patient(admission.getPatient()).Doctor(admission.getDoctor()).ModeOfPayment("CASH").build();

    }
    private int calcualteDayDiff(Date endDate, Date startDate){
         long diff = endDate.getTime() - startDate.getTime();

         long noOfDays  = diff/(1000*60*60*24);
         return (int)noOfDays != 0 ? (int)noOfDays : 1;

    }

}
