package com.hms.controllers;

import com.hms.dto.LoginRequest;
import com.hms.dto.LoginResponse;
import com.hms.dto.UserDetailsDto;
import com.hms.entities.HospitalStaff;
import com.hms.security.JwtTokenHelper;
import com.hms.service.AuthService;
import com.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtHelper;
    @Autowired
    private AuthService authService;

    @RequestMapping("/login")
    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        authService.doAuthenticate(request.getUserName(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.jwtHelper.generateToken(userDetails);

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .status("Success").build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDetailsDto> signUp(@RequestBody HospitalStaff user) {

        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(user));
    }


}
