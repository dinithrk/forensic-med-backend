package com.forensys.backend.service;

import com.forensys.backend.dto.AnalyticsDto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface AnalyticsService {
    DashboardSummary getDashboardSummary(LocalDateTime start, LocalDateTime end);
    List<MonthlyVolumeItem> getMonthlyVolume(LocalDateTime start, LocalDateTime end);
    List<CategoryBreakdownItem> getCategoryBreakdown(LocalDateTime start, LocalDateTime end);
    List<StatusBreakdownItem> getStatusBreakdown(LocalDateTime start, LocalDateTime end);
    TurnaroundMetrics getTurnaroundMetrics(LocalDateTime start, LocalDateTime end);
    List<OfficerWorkload> getOfficerWorkload(LocalDateTime start, LocalDateTime end);
    List<ExaminationTrends> getExaminationTrends(LocalDateTime start, LocalDateTime end);
    TimeDistribution getTimeDistribution(LocalDateTime start, LocalDateTime end);
    AllAnalyticsData getAllAnalyticsData(LocalDateTime start, LocalDateTime end);
    byte[] exportPdf(LocalDateTime start, LocalDateTime end);
    byte[] exportCsv(LocalDateTime start, LocalDateTime end);
}
