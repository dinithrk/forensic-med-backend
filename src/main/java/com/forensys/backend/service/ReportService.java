package com.forensys.backend.service;

import com.forensys.backend.dto.ForensicReportDto;
import com.forensys.backend.dto.ReportNotificationDto;
import com.forensys.backend.dto.ReportStatusUpdateDto;
import com.forensys.backend.entity.enums.ReportStatus;

import java.util.List;

public interface ReportService {
    ForensicReportDto autoGenerateReportDraft(String caseType, Long caseId, String reportType);
    ForensicReportDto saveReport(ForensicReportDto dto);
    ForensicReportDto getReportById(Long id);
    List<ForensicReportDto> getAllReports(ReportStatus status);
    List<ForensicReportDto> getReportsForCase(String caseType, Long caseId);
    ForensicReportDto updateReportStatus(Long id, ReportStatusUpdateDto updateDto);
    ForensicReportDto amendReport(Long id, String amendmentReason, ForensicReportDto dto);
    ReportNotificationDto getNotificationWidgetData();
}
