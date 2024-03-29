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

//    Signup a new HospitalStaff member
    @Override
    public UserDetailsDto signup(HospitalStaff hStaff) {
//        Encrypt the user's password using the PasswordEncoder
        String encryptedPswrd = encoder.encode(hStaff.getPassword());
        hStaff.setPassword(encryptedPswrd);

//        Convert the saved HospitalStaff entity to a UserDetailsDto and return it
        HospitalStaff user =  staffRepo.save(hStaff);
         return convertUsertoDto(user);
    }

    @Override
    public List<UserDetailsDto> getAllStaffMembers() throws Exception {
        // Retrieve details of all staff members
        List<HospitalStaff> allStaff =   Optional.of(staffRepo.findAll()).orElseThrow(() -> new Exception());
        List<UserDetailsDto>  userDetailsDtoList =  allStaff.stream().map(staff -> convertUsertoDto(staff)).collect(Collectors.toList());
        return userDetailsDtoList;
    }

//     Retrieve all admission types
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
