package com.hms.controllers;

import com.hms.dto.LoginRequest;
import com.hms.dto.LoginResponse;
import com.hms.dto.UserDetailsDto;
import com.hms.entities.HospitalStaff;
import com.hms.security.JwtTokenHelper;
import com.hms.service.AuthService;
import com.hms.service.UserService;
import com.hms.validations.UniqueUsername;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Authentication")
public class AuthController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtHelper;
    @Autowired
    private AuthService authService;

     @Operation(
             description = " Login controller",
             summary = "Post request  to login using username and password"
     )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        authService.doAuthenticate(request.getUserName(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.jwtHelper.generateToken(userDetails);

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .status("Success").build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            description = " Signup controller",
            summary = "Post request for hospital staff to add there data in database"
    )
    @PostMapping("/signup")
    public ResponseEntity<UserDetailsDto> signUp(@Valid @RequestBody HospitalStaff user) {

        return  ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(user));
    }


}
