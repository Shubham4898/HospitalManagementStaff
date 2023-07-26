package com.hms.service;

import com.hms.dto.UserDetailsDto;
import com.hms.entities.AdmissionType;
import com.hms.entities.HospitalStaff;

import java.util.List;

public interface UserService {


    UserDetailsDto signup(HospitalStaff hStaff);

    List<UserDetailsDto> getAllStaffMembers() throws Exception;
    List<AdmissionType> getAllAdmissionType();
    UserDetailsDto convertUsertoDto(HospitalStaff user);
}
