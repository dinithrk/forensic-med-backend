package com.forensys.backend.controller;

import com.forensys.backend.dto.ForensicReportDto;
import com.forensys.backend.dto.ManagementReportDto;
import com.forensys.backend.dto.ReportNotificationDto;
import com.forensys.backend.dto.ReportStatusUpdateDto;
import com.forensys.backend.entity.enums.ReportStatus;
import com.forensys.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Forensic Reports", description = "Endpoints for MLR/PMR generation, workflows, and management analytics")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    @Operation(summary = "Auto-generate a draft report from a case")
    public ResponseEntity<ForensicReportDto> autoGenerateReportDraft(
            @RequestParam String caseType,
            @RequestParam Long caseId,
            @RequestParam(required = false, defaultValue = "MLR") String reportType) {
        return new ResponseEntity<>(reportService.autoGenerateReportDraft(caseType, caseId, reportType), HttpStatus.CREATED);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    @Operation(summary = "Save a new report manually")
    public ResponseEntity<ForensicReportDto> saveReport(@Valid @RequestBody ForensicReportDto dto) {
        return new ResponseEntity<>(reportService.saveReport(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get report by ID")
    public ResponseEntity<ForensicReportDto> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get all reports, optionally filtered by status")
    public ResponseEntity<List<ForensicReportDto>> getAllReports(@RequestParam(required = false) ReportStatus status) {
        return ResponseEntity.ok(reportService.getAllReports(status));
    }

    @GetMapping("/case/{caseType}/{caseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get all reports for a specific case")
    public ResponseEntity<List<ForensicReportDto>> getReportsForCase(
            @PathVariable String caseType,
            @PathVariable Long caseId) {
        return ResponseEntity.ok(reportService.getReportsForCase(caseType, caseId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Update report status (e.g., from DRAFT to PENDING_APPROVAL)")
    public ResponseEntity<ForensicReportDto> updateReportStatus(
            @PathVariable Long id,
            @Valid @RequestBody ReportStatusUpdateDto updateDto) {
        return ResponseEntity.ok(reportService.updateReportStatus(id, updateDto));
    }

    @PostMapping("/{id}/amend")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    @Operation(summary = "Amend an existing report")
    public ResponseEntity<ForensicReportDto> amendReport(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "Amended findings") String reason,
            @Valid @RequestBody ForensicReportDto dto) {
        return new ResponseEntity<>(reportService.amendReport(id, reason, dto), HttpStatus.CREATED);
    }

    @GetMapping("/dashboard-notifications")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get dashboard notifications (pending reports, due for court, etc.)")
    public ResponseEntity<ReportNotificationDto> getNotificationWidgetData() {
        return ResponseEntity.ok(reportService.getNotificationWidgetData());
    }

    @GetMapping("/analytics/daily")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get daily report statistics")
    public ResponseEntity<ManagementReportDto.DailyReport> getDailyReport(
            @RequestParam(required = false) String date) {
        return ResponseEntity.ok(reportService.getDailyReport(date));
    }

    @GetMapping("/analytics/monthly")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get monthly report statistics")
    public ResponseEntity<ManagementReportDto.MonthlyReport> getMonthlyReport(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return ResponseEntity.ok(reportService.getMonthlyReport(year, month));
    }

    @GetMapping("/analytics/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get pending reports list")
    public ResponseEntity<ManagementReportDto.PendingReport> getPendingReport() {
        return ResponseEntity.ok(reportService.getPendingReport());
    }

    @GetMapping("/analytics/court")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get pending court dates/summons")
    public ResponseEntity<ManagementReportDto.CourtReport> getCourtReport() {
        return ResponseEntity.ok(reportService.getCourtReport());
    }

    @GetMapping("/analytics/statistics")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    @Operation(summary = "Get overall report statistics")
    public ResponseEntity<ManagementReportDto.StatisticalReport> getStatisticalReport() {
        return ResponseEntity.ok(reportService.getStatisticalReport());
    }
}
