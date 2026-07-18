package com.forensys.backend.repository;

import com.forensys.backend.entity.MlrReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MlrReportRepository extends JpaRepository<MlrReport, Long> {
}
