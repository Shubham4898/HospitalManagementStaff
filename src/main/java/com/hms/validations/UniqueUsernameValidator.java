package com.hms.validations;

import com.hms.entities.HospitalStaff;
import com.hms.repositories.HospitalStaffRepo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {

    @Autowired
    HospitalStaffRepo repo;



    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {
        if(userName == null) return true;
        System.out.println(userName);
        Optional<HospitalStaff> staff = repo.findHospitalStaffByUserName(userName);
        System.out.println(staff.isPresent());
        return !staff.isPresent();
    }
}
