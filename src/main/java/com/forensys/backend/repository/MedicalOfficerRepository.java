package com.forensys.backend.repository;

import com.forensys.backend.entity.MedicalOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalOfficerRepository extends JpaRepository<MedicalOfficer, Long> {
}
