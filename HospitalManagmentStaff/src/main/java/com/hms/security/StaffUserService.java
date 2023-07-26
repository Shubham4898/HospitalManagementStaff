package com.hms.security;

import com.hms.exceptions.ResourceNotFoundException;
import com.hms.entities.HospitalStaff;
import com.hms.repositories.HospitalStaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StaffUserService implements UserDetailsService {

    @Autowired
    private HospitalStaffRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HospitalStaff user = this.repo.findHospitalStaffByUserName(username).orElseThrow(
                () -> new ResourceNotFoundException("user not find with username:-" + username)
        );
       return user;
    }
}
