package com.hms.repositories;

import com.hms.entities.AdmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionTypeRepo extends JpaRepository<AdmissionType,Integer> {

}
