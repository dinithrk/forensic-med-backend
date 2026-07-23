package com.forensys.backend.controller;

import com.forensys.backend.dto.AnalyticsDto.*;
import com.forensys.backend.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/dashboard-summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<DashboardSummary> getDashboardSummary(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getDashboardSummary(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/monthly-volume")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<MonthlyVolumeItem>> getMonthlyVolume(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getMonthlyVolume(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/category-breakdown")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<CategoryBreakdownItem>> getCategoryBreakdown(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getCategoryBreakdown(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/status-breakdown")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<StatusBreakdownItem>> getStatusBreakdown(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getStatusBreakdown(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/turnaround-metrics")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<TurnaroundMetrics> getTurnaroundMetrics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getTurnaroundMetrics(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/officer-workload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OfficerWorkload>> getOfficerWorkload(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getOfficerWorkload(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/exam-trends")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<ExaminationTrends>> getExaminationTrends(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getExaminationTrends(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/time-distribution")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<TimeDistribution> getTimeDistribution(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getTimeDistribution(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<AllAnalyticsData> getAllAnalyticsData(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(analyticsService.getAllAnalyticsData(parseStart(startDate), parseEnd(endDate)));
    }

    @GetMapping("/export/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<byte[]> exportPdf(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        byte[] pdf = analyticsService.exportPdf(parseStart(startDate), parseEnd(endDate));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=forensys_analytics_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/export/csv")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<byte[]> exportCsv(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        byte[] csv = analyticsService.exportCsv(parseStart(startDate), parseEnd(endDate));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=forensys_analytics_report.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }

    private LocalDateTime parseStart(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return LocalDate.now().withDayOfMonth(1).atStartOfDay();
        }
        return LocalDate.parse(dateStr).atStartOfDay();
    }

    private LocalDateTime parseEnd(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            LocalDate now = LocalDate.now();
            return now.withDayOfMonth(now.lengthOfMonth()).atTime(LocalTime.MAX);
        }
        return LocalDate.parse(dateStr).atTime(LocalTime.MAX);
    }
}
