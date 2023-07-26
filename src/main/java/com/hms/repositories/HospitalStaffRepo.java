package com.hms.repositories;

import com.hms.entities.HospitalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface HospitalStaffRepo extends JpaRepository<HospitalStaff,Long> {

    Optional<HospitalStaff> findHospitalStaffByUserName(String userName);
}
