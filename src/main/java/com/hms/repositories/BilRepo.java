package com.hms.repositories;

import com.hms.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BilRepo extends JpaRepository<Bill,Integer> {
}
