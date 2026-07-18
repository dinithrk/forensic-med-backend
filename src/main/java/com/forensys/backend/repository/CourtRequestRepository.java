package com.forensys.backend.repository;

import com.forensys.backend.entity.CourtRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtRequestRepository extends JpaRepository<CourtRequest, Long> {
}
