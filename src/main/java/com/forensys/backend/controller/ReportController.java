package com.forensys.backend.controller;

import com.forensys.backend.dto.ForensicReportDto;
import com.forensys.backend.dto.ReportNotificationDto;
import com.forensys.backend.dto.ReportStatusUpdateDto;
import com.forensys.backend.entity.enums.ReportStatus;
import com.forensys.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/generate")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<ForensicReportDto> autoGenerateReportDraft(
            @RequestParam String caseType,
            @RequestParam Long caseId,
            @RequestParam(required = false, defaultValue = "MLR") String reportType) {
        return new ResponseEntity<>(reportService.autoGenerateReportDraft(caseType, caseId, reportType), HttpStatus.CREATED);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<ForensicReportDto> saveReport(@RequestBody ForensicReportDto dto) {
        return new ResponseEntity<>(reportService.saveReport(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<ForensicReportDto> getReportById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<ForensicReportDto>> getAllReports(@RequestParam(required = false) ReportStatus status) {
        return ResponseEntity.ok(reportService.getAllReports(status));
    }

    @GetMapping("/case/{caseType}/{caseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<List<ForensicReportDto>> getReportsForCase(
            @PathVariable String caseType,
            @PathVariable Long caseId) {
        return ResponseEntity.ok(reportService.getReportsForCase(caseType, caseId));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<ForensicReportDto> updateReportStatus(
            @PathVariable Long id,
            @RequestBody ReportStatusUpdateDto updateDto) {
        return ResponseEntity.ok(reportService.updateReportStatus(id, updateDto));
    }

    @PostMapping("/{id}/amend")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER')")
    public ResponseEntity<ForensicReportDto> amendReport(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "Amended findings") String reason,
            @RequestBody ForensicReportDto dto) {
        return new ResponseEntity<>(reportService.amendReport(id, reason, dto), HttpStatus.CREATED);
    }

    @GetMapping("/dashboard-notifications")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'CLERICAL_OFFICER')")
    public ResponseEntity<ReportNotificationDto> getNotificationWidgetData() {
        return ResponseEntity.ok(reportService.getNotificationWidgetData());
    }
}
