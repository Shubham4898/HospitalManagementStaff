package com.hms.controllers;

import com.hms.dto.LoginRequest;
import com.hms.dto.LoginResponse;
import com.hms.dto.UserDetailsDto;
import com.hms.entities.AdmissionType;
import com.hms.entities.HospitalStaff;
import com.hms.security.JwtTokenHelper;
import com.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/all")
    public ResponseEntity<List<UserDetailsDto>> getAllStaffMembers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllStaffMembers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/admissionType")
    ResponseEntity<List<AdmissionType>> getAllAdmissionTye(){
      return  ResponseEntity.ok().body(userService.getAllAdmissionType());
    }


}
