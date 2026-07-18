package com.forensys.backend.repository;

import com.forensys.backend.entity.LaboratoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboratoryRequestRepository extends JpaRepository<LaboratoryRequest, Long> {
}
