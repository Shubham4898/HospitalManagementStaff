package com.hms.service.impl;

import com.hms.dto.UserDetailsDto;
import com.hms.entities.AdmissionType;
import com.hms.entities.HospitalStaff;
import com.hms.repositories.AdmissionTypeRepo;
import com.hms.repositories.HospitalStaffRepo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserServiceImplTest {

    @Mock
    private HospitalStaffRepo staffRepo;

    @Mock
    private AdmissionTypeRepo admissionTypeRepo;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup() {
        // Create a sample HospitalStaff for signup
        HospitalStaff hospitalStaff = new HospitalStaff();
        hospitalStaff.setUserName("testuser");
        hospitalStaff.setPassword("testpassword");

        // Mock the encoder to return a fixed encrypted password
        when(encoder.encode(anyString())).thenReturn("encryptedPassword");

        // Mock the staffRepo to return the saved HospitalStaff
        when(staffRepo.save(any(HospitalStaff.class))).thenReturn(hospitalStaff);

        // Perform the signup
        UserDetailsDto userDetailsDto = userService.signup(hospitalStaff);

        // Verify the password was encrypted and staffRepo.save was called
        verify(encoder).encode("testpassword");
        verify(staffRepo).save(hospitalStaff);

        // Check that the UserDetailsDto is returned with the correct username and encrypted password
        assertEquals("testuser", userDetailsDto.getUserName());
        assertEquals("encryptedPassword", hospitalStaff.getPassword());
    }

    @Test
    void testGetAllStaffMembers() throws Exception {
        // Create sample HospitalStaff data

        HospitalStaff staff1 = new HospitalStaff();
        staff1.setUserName("user1");
        HospitalStaff staff2 = new HospitalStaff();
        staff2.setUserName("user2");
        List<HospitalStaff> allStaff = Arrays.asList(staff1, staff2);

        // Mock the staffRepo to return the sample data
        when(staffRepo.findAll()).thenReturn(allStaff);

        // Perform the getAllStaffMembers
        List<UserDetailsDto> userDetailsList = userService.getAllStaffMembers();

        // Check that staffRepo.findAll was called
        verify(staffRepo).findAll();

        // Check that UserDetailsDto list contains correct usernames
        assertEquals(2, userDetailsList.size());
        assertEquals("user1", userDetailsList.get(0).getUserName());
        assertEquals("user2", userDetailsList.get(1).getUserName());
    }

    @Test
    void testGetAllAdmissionType() {
        // Create sample AdmissionType data
        AdmissionType admissionType1 = new AdmissionType();
        admissionType1.setId(1);
        admissionType1.setType("Type 1");
        AdmissionType admissionType2 = new AdmissionType();
        admissionType2.setId(2);
        admissionType2.setType("Type 2");
        List<AdmissionType> allAdmissionTypes = Arrays.asList(admissionType1, admissionType2);

        // Mock the admissionTypeRepo to return the sample data
        when(admissionTypeRepo.findAll()).thenReturn(allAdmissionTypes);

        // Perform the getAllAdmissionType
        List<AdmissionType> result = userService.getAllAdmissionType();

        // Check that admissionTypeRepo.findAll was called
        verify(admissionTypeRepo).findAll();

        // Check that the result contains the correct number of admission types and their IDs
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }


}
