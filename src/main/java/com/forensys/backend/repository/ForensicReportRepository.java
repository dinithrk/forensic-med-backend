package com.forensys.backend.repository;

import com.forensys.backend.entity.ForensicReport;
import com.forensys.backend.entity.enums.ReportStatus;
import com.forensys.backend.entity.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ForensicReportRepository extends JpaRepository<ForensicReport, Long> {

    List<ForensicReport> findByMlefRecord_MlefIdOrderByVersionNumberDescCreatedAtDesc(Long mlefId);

    List<ForensicReport> findByPostMortem_PmSerialNoOrderByVersionNumberDescCreatedAtDesc(Long pmSerialNo);

    List<ForensicReport> findByStatus(ReportStatus status);

    List<ForensicReport> findByReportType(ReportType reportType);

    @Query("SELECT r FROM ForensicReport r WHERE r.dateOfTrial >= :startDate ORDER BY r.dateOfTrial ASC")
    List<ForensicReport> findUpcomingCourtDates(@Param("startDate") LocalDate startDate);

    @Query("SELECT r FROM ForensicReport r WHERE r.status = 'FINALIZED' ORDER BY r.finalizedDate ASC")
    List<ForensicReport> findPendingDispatches();
}
