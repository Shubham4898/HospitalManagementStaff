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


    // Admit a patient and create an admission record
    @Override
    public AdmissionDto admit(AdmitPatientDto admitPatientDto, long patientId) {

//         Fetch the patient based on the provided patientId
        Patient patient =  patientRepo.findById(patientId).orElseThrow(
                () -> new ResourceNotFoundException("No Patient Found with id:-" + patientId)
        );
//         Fetch the doctor based on the provided doctorId in AdmitPatientDto
        HospitalStaff doctor = hospitalStaffRepo.findById(admitPatientDto.getDoctorId()).orElseThrow(
                () -> new ResourceNotFoundException("No Doctor found with id:-" + admitPatientDto.getDoctorId())

        );
//        Fetch the admission type based on the provided admissionTypeId in AdmitPatientDto
        AdmissionType admissionType =admissionTypeRepo.findById(admitPatientDto.getAdmissionTypeId()).orElseThrow(
                () -> new ResourceNotFoundException("No AdmissionType with  id:-" + admitPatientDto.getAdmissionTypeId())
        );
//        Create an Admission entity and save it to the database
        Admission admission=  admissionRepo.save(Admission.builder().admissionDate(new Date()).admissionType(admissionType)
                .doctor(doctor).status(AdmissionStatus.ADMITTED).patient(patient)
                .roomNo(admitPatientDto.getRoomNo()).build());
//         Map the Admission entity to AdmissionDto and return it
        return  AdmissionDto.builder().dcotorId(doctor.getId()).admissionDate(admission.getAdmissionDate())
                .admissionType(admission.getAdmissionType()).status(admission.getStatus())
                .roomNo(admission.getRoomNo()).patient(admission.getPatient()).build();


    }

//     Discharge a patient and create a bill
    @Override
    public BillDto discharge(int admissionId) {
//         Fetch the admission based on the provided admissionId
        Admission admission = admissionRepo.findById(admissionId).orElseThrow(
                () -> new ResourceNotFoundException("No admission for this id:-" + admissionId)
        );
//         Update the admission status to "DISCHARGED" and set the discharged date
        admission.setDischargedDate(new Date());
        admission.setStatus(AdmissionStatus.DISCHARGED);

//         Calculate the bill amount based on the admission details
        Bill bill = calculateBill(admission);
//         Save the bill to the database
        Bill finalBill =  bilRepo.save(bill);

//        Map the Bill entity to BillDto and return it
        return BillDto.builder().amount(bill.getAmount()).id(bill.getId())
                .Doctor(userService.convertUsertoDto(bill.getDoctor()))
                .patient(bill.getPatient()).ModeOfPayment(bill.getModeOfPayment()).build();


    }

    // Calculate bill amount based on admission details
    private Bill calculateBill(Admission admission){

        AdmissionType  type = admission.getAdmissionType();
        Date date = new Date();
        // Calculate the duration of the patient's stay in days
        int noOfAdmitDate = calcualteDayDiff(admission.getDischargedDate(),admission.getAdmissionDate());

//         Calculate the bill amount based on admission type's fixed charge and per-day charge
        Double amount = type.getFixedCharge() + type.getPerDayCharge()*noOfAdmitDate;

        return  Bill.builder().amount(amount).patient(admission.getPatient()).Doctor(admission.getDoctor()).ModeOfPayment("CASH").build();

    }

//     Calculate the difference in days between two dates
    private int calcualteDayDiff(Date endDate, Date startDate){
         long diff = endDate.getTime() - startDate.getTime();

         long noOfDays  = diff/(1000*60*60*24);

//         if person discharded on same days then it will be considered as 1 day
         return (int)noOfDays != 0 ? (int)noOfDays : 1;

    }

}
