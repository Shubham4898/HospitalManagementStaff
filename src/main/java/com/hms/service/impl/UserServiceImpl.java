package com.hms.service.impl;

import com.hms.dto.UserDetailsDto;
import com.hms.entities.AdmissionType;
import com.hms.entities.HospitalStaff;
import com.hms.entities.User;
import com.hms.repositories.AdmissionTypeRepo;
import com.hms.repositories.HospitalStaffRepo;
import com.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private HospitalStaffRepo staffRepo;

    @Autowired
    private AdmissionTypeRepo admissionTypeRepo;

    @Autowired
    private PasswordEncoder encoder;
    @Override
    public UserDetailsDto signup(HospitalStaff hStaff) {
        String encryptedPswrd = encoder.encode(hStaff.getPassword());
        hStaff.setPassword(encryptedPswrd);
        HospitalStaff user =  staffRepo.save(hStaff);
         return convertUsertoDto(user);
    }

    @Override
    public List<UserDetailsDto> getAllStaffMembers() throws Exception {
        List<HospitalStaff> allStaff =   Optional.of(staffRepo.findAll()).orElseThrow(() -> new Exception());
        List<UserDetailsDto>  userDetailsDtoList =  allStaff.stream().map(staff -> convertUsertoDto(staff)).collect(Collectors.toList());
        return userDetailsDtoList;
    }

    @Override
    public List<AdmissionType> getAllAdmissionType() {
       return admissionTypeRepo.findAll();
    }

    @Override
    public UserDetailsDto convertUsertoDto(HospitalStaff user){
       return  UserDetailsDto.builder().userName(user.getUsername())
                .age(user.getAge())
                .address(user.getAddress())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber()).id(user.getId()).build();
    }
}
