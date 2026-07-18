package com.forensys.backend.repository;

import com.forensys.backend.entity.IndividualInjury;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualInjuryRepository extends JpaRepository<IndividualInjury, Long> {
}
