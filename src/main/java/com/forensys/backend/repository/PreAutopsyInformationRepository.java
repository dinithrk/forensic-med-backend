package com.forensys.backend.repository;

import com.forensys.backend.entity.PreAutopsyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreAutopsyInformationRepository extends JpaRepository<PreAutopsyInformation, Long> {
}
