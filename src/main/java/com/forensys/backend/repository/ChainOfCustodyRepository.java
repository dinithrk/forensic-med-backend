package com.forensys.backend.repository;

import com.forensys.backend.entity.ChainOfCustody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChainOfCustodyRepository extends JpaRepository<ChainOfCustody, Long> {
}
